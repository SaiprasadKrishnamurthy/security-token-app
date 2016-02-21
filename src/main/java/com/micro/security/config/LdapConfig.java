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

    /**
     * This bean starts an embedded LDAP Server. Note that <code>start</code>
     * is not called on the server as the same is done as part of the bean life cycle's
     * afterPropertySet() method.
     * @return The Embedded Ldap Server
     * @throws Exception
     *//*
    @Bean(name = "ldap-server")
    public ApacheDSContainer getLdapServer() throws Exception {
        13.
        ApacheDSContainer container = new ApacheDSContainer("o=welflex",
                14.
                "classpath:flightcontrol.ldiff");
        15.
        container.setPort(EMBEDDED_LDAP_SERVER_PORT);
        16.
        return container;
        17.
    }*/
}
