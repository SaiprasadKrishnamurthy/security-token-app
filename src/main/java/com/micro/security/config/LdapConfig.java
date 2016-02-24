package com.micro.security.config;

import com.micro.security.AppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import javax.inject.Inject;

/**
 * Created by saipkri on 19/02/16.
 */
@Configuration
@Profile("ldap")
public class LdapConfig {

    @Inject
    private AppProperties appProperties;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(appProperties.getLdapUrl());
        contextSource.setBase(appProperties.getLdapBase());
        contextSource.setUserDn(appProperties.getLdapUser());
        contextSource.setPassword(appProperties.getLdapPassword());
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}
