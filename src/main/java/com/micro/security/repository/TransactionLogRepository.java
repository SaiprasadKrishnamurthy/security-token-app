package com.micro.security.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by saipkri on 21/02/16.
 */
@Repository
public class TransactionLogRepository {

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public TransactionLogRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void log(String uri, String id, String verb, long expiresAfter) {
        if (numberOfRequests(uri, id, verb) == 0) {
            System.out.println("First insert --- ");
            Calendar timeout = Calendar.getInstance();
            timeout.setTimeInMillis(new Date().getTime() + (expiresAfter * 1000));
            String sql = "INSERT INTO TRANSACTION_LOG(USER_ID, URI, VERB, NUMBEROFTRANSACTIONS, START_TIME, END_TIME) VALUES (?,?,?,?,?,?)";
            jdbcTemplate.update(sql, id, uri, verb, 1, new Date(), timeout.getTime());
        } else {
            String sql = "SELECT NUMBEROFTRANSACTIONS FROM TRANSACTION_LOG WHERE USER_ID=? AND URI=? AND VERB=?";
            int noOfRequests = jdbcTemplate.queryForObject(sql, new Object[]{id, uri, verb}, Integer.class);
            System.out.println(" Update --> " + noOfRequests);
            String update = "UPDATE TRANSACTION_LOG SET NUMBEROFTRANSACTIONS=? WHERE USER_ID=? AND URI=? AND VERB=?";
            jdbcTemplate.update(update, noOfRequests + 1, id, uri, verb);
        }
    }

    public int numberOfRequests(final String uri, final String userId, final String verb) {
        deleteLogIfExpired(uri, userId, verb);
        String sql = "SELECT NUMBEROFTRANSACTIONS FROM TRANSACTION_LOG WHERE USER_ID=? AND URI=? AND VERB=?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{userId, uri, verb});
        if (rows.isEmpty()) {
            return 0;
        } else {
            return (Integer) rows.get(0).get("NUMBEROFTRANSACTIONS");
        }
    }

    private void deleteLogIfExpired(final String uri, final String userId, final String verb) {
        String sql = "SELECT END_TIME FROM TRANSACTION_LOG WHERE USER_ID=? AND URI=? AND VERB=?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{userId, uri, verb});

        if (!rows.isEmpty() && new Date().after((Date) rows.get(0).get("END_TIME"))) {
            System.out.println("Deleted ---- ");
            String delete = "DELETE FROM TRANSACTION_LOG WHERE USER_ID=? AND URI=? AND VERB=?";
            jdbcTemplate.update(delete, userId, uri, verb);

        }
    }
}
