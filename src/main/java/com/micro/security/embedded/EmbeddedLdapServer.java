package com.micro.security.embedded;

import org.apache.commons.io.FileUtils;
import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.DirectoryService;
import org.apache.directory.server.core.entry.ServerEntry;
import org.apache.directory.server.core.partition.Partition;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.store.LdifFileLoader;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;
import org.apache.directory.shared.ldap.entry.Entry;
import org.apache.directory.shared.ldap.exception.LdapNameNotFoundException;
import org.apache.directory.shared.ldap.name.LdapDN;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A simple example exposing how to embed Apache Directory Server
 * into an application.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$
 */
@Component
@Profile(value = {"ldap", "nonprod"})
public class EmbeddedLdapServer {
    /**
     * The directory service
     */
    private DirectoryService service;
    private static LdapServer ldapServer;


    /**
     * Add a new partition to the server
     *
     * @param partitionId The partition Id
     * @param partitionDn The partition DN
     * @return The newly added partition
     * @throws Exception If the partition can't be added
     */
    private Partition addPartition(String partitionId, String partitionDn) throws Exception {
        // Create a new partition named 'foo'.
        Partition partition = new JdbmPartition();
        partition.setId(partitionId);
        partition.setSuffix(partitionDn);
        service.addPartition(partition);

        return partition;
    }

    /**
     * Initialize the server. It creates the partition, adds the index, and
     * injects the context entries for the created partitions.
     *
     * @throws Exception if there were some problems while initializing the system
     */
    private void init() throws Exception {

        // Initialize the LDAP service
        service = new DefaultDirectoryService();

        FileUtils.deleteQuietly(service.getWorkingDirectory());

        // Disable the ChangeLog system
        service.getChangeLog().setEnabled(false);
        service.setDenormalizeOpAttrsEnabled(true);

        Partition barPartition = addPartition("security", "dc=securityapp,dc=org");
        ldapServer = new LdapServer();
        ldapServer.setTransports(new TcpTransport(11389));
        ldapServer.setDirectoryService(service);

        // And start the service
        service.startup();
        ldapServer.start();

        // Inject the bar root entry
        try {
            service.getAdminSession().lookup(barPartition.getSuffixDn());
        } catch (LdapNameNotFoundException lnnfe) {
            LdapDN dnBar = new LdapDN("dc=securityapp,dc=org");
            ServerEntry entryBar = service.newEntry(dnBar);
            entryBar.add("objectClass", "top", "domain", "extensibleObject");
            entryBar.add("dc", "securityapp");
            service.getAdminSession().add(entryBar);
        }

        applyLdif(new File(EmbeddedLdapServer.class.getClassLoader().getResource("ldif/test-ldap-tree.ldif").toURI().getPath()));
    }

    public void applyLdif(final File ldifFile) throws Exception {
        new LdifFileLoader(service.getAdminSession(), ldifFile, null).execute();
    }

    /**
     * Creates a new instance of EmbeddedADS. It initializes the directory service.
     *
     * @throws Exception If something went wrong
     */
    public EmbeddedLdapServer() throws Exception {
        init();
    }

    /**
     * Main class. We just do a lookup on the server to check that it's available.
     *
     * @param args Not used.
     */
    public static void main(String[] args) //throws Exception
    {
        try {
            // Create the server
            EmbeddedLdapServer ads = new EmbeddedLdapServer();

            // Read an entry
            Entry result = ads.service.getAdminSession().lookup(new LdapDN("cn=Sai Kris,ou=Users,dc=securityapp,dc=org"));

            // And print it if available
            System.out.println("Found entry : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
