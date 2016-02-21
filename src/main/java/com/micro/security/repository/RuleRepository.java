package com.micro.security.repository;

import com.micro.security.model.AccessRule;
import com.micro.security.model.NumberOfTransactionsRule;
import com.micro.security.model.PermissionRule;
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
        String sql = "INSERT INTO access_rule(id, uri, permissionrule_requiredpermissions, permissionrule_minimumnumberofpermissionsmatch, tokenexpirationrule_permissiontoken, tokenexpirationrule_expirationtimeinseconds, numberoftransactionsrule_permissiontoken, numberoftransactionsrule_maxnumberoftransactionsallowed, numberoftransactionsrule_untiltime) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                UUID.randomUUID().toString(),
                accessRule.getUri(),
                toCsv(accessRule.getPermissionRule().getRequiredPermissions()),
                accessRule.getPermissionRule().getMinimumNumberOfPermissionsMatch(),
                accessRule.getNumberOfTransactionsRule().getPermissionToken(),
                accessRule.getNumberOfTransactionsRule().getMaxNumberOfTransactionsAllowed(),
                accessRule.getNumberOfTransactionsRule().getUntilTime()
        );
    }

    public List<AccessRule> allAccessRules() {
        String sql = "SELECT id, uri, permissionrule_requiredpermissions, permissionrule_minimumnumberofpermissionsmatch, tokenexpirationrule_permissiontoken, tokenexpirationrule_expirationtimeinseconds, numberoftransactionsrule_permissiontoken, numberoftransactionsrule_maxnumberoftransactionsallowed, numberoftransactionsrule_untiltime FROM ACCESS_RULE";
        return jdbcTemplate.query(sql, new RowMapper<AccessRule>() {

            @Override
            public AccessRule mapRow(final ResultSet resultSet, final int i) throws SQLException {
                String id = resultSet.getString("id");
                String uri = resultSet.getString("uri");
                String permissionRuleRequiredPermissions = resultSet.getString("permissionrule_requiredpermissions");
                Integer permissionRuleMinimumNumberOfPermissionsMatch = resultSet.getInt("permissionrule_minimumnumberofpermissionsmatch");
                String numberOfTransactionsRulePermissionToken = resultSet.getString("numberoftransactionsrule_permissiontoken");
                Integer numberOfTransactionsRuleAllowedNumberOfTransactions = resultSet.getInt("numberoftransactionsrule_maxnumberoftransactionsallowed");
                Long numberOfTransactionsRuleTimeUntil = resultSet.getLong("numberoftransactionsrule_untiltime");

                PermissionRule permissionRule = new PermissionRule();
                permissionRule.setRequiredPermissions(Arrays.asList(permissionRuleRequiredPermissions.split(",")));
                permissionRule.setMinimumNumberOfPermissionsMatch(permissionRuleMinimumNumberOfPermissionsMatch);

                NumberOfTransactionsRule numberOfTransactionsRule = new NumberOfTransactionsRule();
                numberOfTransactionsRule.setMaxNumberOfTransactionsAllowed(numberOfTransactionsRuleAllowedNumberOfTransactions);
                numberOfTransactionsRule.setUntilTime(numberOfTransactionsRuleTimeUntil);

                AccessRule accessRule = new AccessRule();
                accessRule.setId(id);
                accessRule.setUri(uri);

                accessRule.setPermissionRule(permissionRule);
                accessRule.setNumberOfTransactionsRule(numberOfTransactionsRule);

                return accessRule;
            }
        });
    }

    private static String toCsv(final List<String> list) {
        return list.stream().collect(joining(","));
    }
}
