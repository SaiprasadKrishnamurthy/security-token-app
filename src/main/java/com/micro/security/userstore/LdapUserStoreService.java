package com.micro.security.userstore;

import com.micro.security.model.AuthenticationFailedException;
import com.micro.security.model.User;
import com.micro.security.model.UserStoreService;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saipkri on 19/02/16.
 */
@Service
public class LdapUserStoreService implements UserStoreService {

    private class UserAttributesMapper implements AttributesMapper<User> {

        @Override
        public User mapFromAttributes(Attributes attributes) throws NamingException {
            User user;
            if (attributes == null) {
                return null;
            }
            user = new User();
            user.setCn(attributes.get("cn").get().toString());
            if (attributes.get("uid") != null) {
                user.setId(attributes.get("uid").get().toString());
            }
            if (attributes.get("sn") != null) {
                user.setSn(attributes.get("sn").get().toString());
            }
            if (attributes.get("postalAddress") != null) {
                user.setPostalAddress(attributes.get("postalAddress").get().toString());
            }
            if (attributes.get("telephoneNumber") != null) {
                user.setTelephoneNumber(attributes.get("telephoneNumber").get().toString());
            }
            if (attributes.get("businessCategory") != null) {
                List<String> permissions = new ArrayList<>();
                NamingEnumeration<?> attrs = attributes.get("businessCategory").getAll();
                while(attrs.hasMoreElements()) {
                    permissions.add(attrs.nextElement().toString());
                }
                user.setPermissionTokens(permissions);
            }
            return user;
        }
    }

    private final LdapTemplate ldapTemplate;

    @Inject
    public LdapUserStoreService(final LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }


    @Override
    public User authenticateAndGetUserDetails(final String username, final String password) {
        if (!ldapTemplate.authenticate("ou=Users", "(uid=" + username + ")", password)) {
            throw new AuthenticationFailedException("Invalid username/password");
        }

        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        List<User> users = ldapTemplate.search("ou=Users", "(uid=" + username + ")", controls, new UserAttributesMapper());
        System.out.println(users);

        System.out.println(" --------- success --------- ");
        return users.get(0);
    }
}
