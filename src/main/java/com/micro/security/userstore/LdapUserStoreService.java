package com.micro.security.userstore;

import com.micro.security.model.User;
import com.micro.security.model.UserStoreService;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by saipkri on 19/02/16.
 */
@Service
public class LdapUserStoreService implements UserStoreService {

    private final LdapTemplate ldapTemplate;

    @Inject
    public LdapUserStoreService(final LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }


    @Override
    public User authenticateAndGetUserDetails(String username, String password) {
        return null;
    }
}
