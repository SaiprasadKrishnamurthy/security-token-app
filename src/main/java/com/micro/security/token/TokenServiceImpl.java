package com.micro.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.security.AppProperties;
import com.micro.security.model.*;
import com.micro.security.repository.RuleRepository;
import com.micro.security.repository.TransactionLogRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by saipkri on 20/02/16.
 */
@Component
public class TokenServiceImpl implements TokenService {

    private final AppProperties appProperties;
    private final RuleRepository ruleRepository;
    private final TransactionLogRepository transactionLogRepository;
    private static final AntPathMatcher MATCHER = new AntPathMatcher("/");


    @Inject
    public TokenServiceImpl(final AppProperties appProperties, final RuleRepository ruleRepository, final TransactionLogRepository transactionLogRepository) {
        this.appProperties = appProperties;
        this.ruleRepository = ruleRepository;
        this.transactionLogRepository = transactionLogRepository;
    }

    @Override
    public String getTokenForUser(final User user) {
        SecretKey key = new SecretKeySpec(appProperties.getSecret().getBytes(), SignatureAlgorithm.forName(appProperties.getSignatureAlgorithm()).toString());
        Calendar timeout = Calendar.getInstance();
        timeout.setTimeInMillis(new Date().getTime() + (appProperties.getExpiresAfterSeconds() * 1000));
        List<AccessRule> accessRules = ruleRepository.allAccessRules();
        String token = Jwts.builder()
                .setSubject(user.getId())
                .setExpiration(timeout.getTime())
                .claim("user", user)
                .claim("accessRules", accessRules)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return token;
    }

    @Override
    public void validateToken(final String token, final String uri, final String verb) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(new SecretKeySpec(appProperties.getSecret().getBytes(), SignatureAlgorithm.forName(appProperties.getSignatureAlgorithm()).toString()))
                    .parseClaimsJws(token).getBody();

            // Get all access rules.
            Object allAccessRules = body.get("accessRules");
            Object userObj = body.get("user");
            ObjectMapper mapper = new ObjectMapper();
            AccessRule[] accessRules = mapper.convertValue(allAccessRules, AccessRule[].class);
            User user = mapper.convertValue(userObj, User.class);
            System.out.println(accessRules.length);
            System.out.println(verb);

            // Pick the best access rule for this URI.
            Optional<AccessRule> accessRuleOptional = Stream.of(accessRules)
                    .peek(a -> System.out.println(" ___>" + a.getUri()))
                    .peek(a -> System.out.println("--> " + uri))
                    .peek(a -> System.out.println("**> " + MATCHER.match(a.getUri(), uri)))
                    .peek(a -> System.out.println("_________> " + a.getHttpMethod()))
                    .peek(a -> System.out.println("######> " + verb.equalsIgnoreCase(a.getHttpMethod())))
                    .filter(accessRule -> MATCHER.match(accessRule.getUri(), uri) && verb.equalsIgnoreCase(accessRule.getHttpMethod()))
                    .findFirst();
            System.out.println(" ---> " + accessRuleOptional);
            if (accessRuleOptional.isPresent() && accessRuleOptional.get().getPermissionRule() != null) {
                List<String> expectedPermissions = accessRuleOptional.get().getPermissionRule().getRequiredPermissions();
                List<String> actualPermissions = user.getPermissionTokens();
                List intersection = ListUtils.intersection(expectedPermissions, actualPermissions);
                boolean permissionsMatched = (intersection.size() > 0 && (accessRuleOptional.get().getPermissionRule().isLenient() || intersection.size() == expectedPermissions.size()));

                if (!permissionsMatched) {
                    throw new InsufficientPrivilegesException("Insufficient priviliges to access the resource: " + uri + " using http verb: " + verb);
                }

                System.out.println("Permissions checks passed -- ");
                if (accessRuleOptional.get().getNumberOfTransactionsRule() != null) {
                    int noOfTransactions = transactionLogRepository.numberOfRequests(uri, user.getId(), verb);
                    System.out.println("no of req ==> " + noOfTransactions);
                    if (noOfTransactions > accessRuleOptional.get().getNumberOfTransactionsRule().getMaxNumberOfTransactionsAllowed() - 1) {
                        throw new TransactionsQuotaExceededException("Quota exceeded");
                    }
                    transactionLogRepository.log(uri, user.getId(), verb, accessRuleOptional.get().getNumberOfTransactionsRule().getTimeAllowedInSeconds());
                }
            }
        } catch (MalformedJwtException exception) {
            throw new IllegalArgumentException("Token is invalid");
        }
    }
}
