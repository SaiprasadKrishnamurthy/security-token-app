package com.micro.security.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

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

    public void log() {

    }
}
