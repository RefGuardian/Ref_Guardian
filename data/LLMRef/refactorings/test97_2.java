public class test97 {

    @Bean
    LdapConnectionDetails ldapConnectionDetails() {
        return createLdapConnectionDetails();
    }

    private LdapConnectionDetails createLdapConnectionDetails() {
        return new LdapConnectionDetails() {

            @Override
            public String[] getUrls() {
                return new String[] { "ldaps://ldap.example.com" };
            }

            @Override
            public String getBase() {
                return "dc=base";
            }

            @Override
            public String getUsername() {
                return "ldap-user";
            }

            @Override
            public String getPassword() {
                return "ldap-password";
            }
        };
    }
}
