package com.micro.security.token;

import com.micro.security.AppProperties;
import com.micro.security.model.AccessRule;
import com.micro.security.model.TokenService;
import com.micro.security.model.User;
import com.micro.security.repository.RuleRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by saipkri on 20/02/16.
 */
@Component
public class TokenServiceImpl implements TokenService {

    private final AppProperties appProperties;
    private final RuleRepository ruleRepository;

    @Inject
    public TokenServiceImpl(final AppProperties appProperties, final RuleRepository ruleRepository) {
        this.appProperties = appProperties;
        this.ruleRepository = ruleRepository;
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
}
