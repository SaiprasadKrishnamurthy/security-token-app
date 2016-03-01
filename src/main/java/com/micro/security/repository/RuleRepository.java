package com.micro.security.repository;

import com.micro.security.model.AccessRule;
import com.micro.security.model.NotFoundException;
import com.micro.security.model.NumberOfTransactionsRule;
import com.micro.security.model.PermissionRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

/**
 * Created by saipkri on 21/02/16.
 */
@Repository
public class RuleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public RuleRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final AccessRule accessRule) {
        String sql = "INSERT INTO access_rule(id, uri, http_method, permissionrule_requiredpermissions, permissionrule_lenient, numberoftransactionsrule_maxnumberoftransactionsallowed, numberoftransactionsrule_timeallowedinseconds) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                UUID.randomUUID().toString(),
                accessRule.getUri(),
                accessRule.getHttpMethod(),
                toCsv(accessRule.getPermissionRule().getRequiredPermissions()),
                accessRule.getPermissionRule().isLenient(),
                accessRule.getNumberOfTransactionsRule().getMaxNumberOfTransactionsAllowed(),
                accessRule.getNumberOfTransactionsRule().getTimeAllowedInSeconds()
        );
    }

    public List<AccessRule> allAccessRules() {
        String sql = "SELECT id, uri, http_method, permissionrule_requiredpermissions, permissionrule_lenient, numberoftransactionsrule_permissiontoken, numberoftransactionsrule_maxnumberoftransactionsallowed, numberoftransactionsrule_timeallowedinseconds FROM ACCESS_RULE";
        return jdbcTemplate.query(sql, new RowMapper<AccessRule>() {

            @Override
            public AccessRule mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return getAccessRule(resultSet);
            }
        });
    }

    private AccessRule getAccessRule(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String uri = resultSet.getString("uri");
        String httpMethod = resultSet.getString("http_method");
        String permissionRuleRequiredPermissions = resultSet.getString("permissionrule_requiredpermissions");
        boolean permissionRuleLenient = resultSet.getBoolean("permissionrule_lenient");
        Integer numberOfTransactionsRuleAllowedNumberOfTransactions = resultSet.getInt("numberoftransactionsrule_maxnumberoftransactionsallowed");
        Integer numberOfTransactionsRuleTimeUntil = resultSet.getInt("numberoftransactionsrule_timeallowedinseconds");

        PermissionRule permissionRule = new PermissionRule();
        permissionRule.setRequiredPermissions(Arrays.asList(permissionRuleRequiredPermissions.split(",")));
        permissionRule.setLenient(permissionRuleLenient);

        NumberOfTransactionsRule numberOfTransactionsRule = new NumberOfTransactionsRule();
        numberOfTransactionsRule.setMaxNumberOfTransactionsAllowed(numberOfTransactionsRuleAllowedNumberOfTransactions);
        numberOfTransactionsRule.setTimeAllowedInSeconds(numberOfTransactionsRuleTimeUntil);

        AccessRule accessRule = new AccessRule();
        accessRule.setUid(id);
        accessRule.setUri(uri);
        accessRule.setHttpMethod(httpMethod);

        accessRule.setPermissionRule(permissionRule);
        accessRule.setNumberOfTransactionsRule(numberOfTransactionsRule);

        return accessRule;
    }

    private static String toCsv(final List<String> list) {
        return list.stream().collect(joining(","));
    }

    public AccessRule findById(final String ruleId) {
        String sql = "SELECT * FROM ACCESS_RULE where id=?";
        List<AccessRule> accessRules = jdbcTemplate.query(sql, new Object[]{ruleId}, new RowMapper<AccessRule>() {
            @Override
            public AccessRule mapRow(final ResultSet resultSet, final int i) throws SQLException {
                return getAccessRule(resultSet);
            }
        });
        // It's silly but ok for now.
        return accessRules.stream()
                .filter(rule -> rule.getUid().equals(ruleId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Rule not found for id: "+ruleId));
    }
}
