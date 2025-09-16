public class test75 {

    private static final String[] PREFIX_VALUES = {
            PREFIX + ".foo.signing.credentials[0].private-key-location=classpath:private-key-location",
            PREFIX + ".foo.signing.credentials[0].certificate-location=classpath:certificate-location",
            PREFIX + ".foo.decryption.credentials[0].private-key-location=classpath:private-key-location",
            PREFIX + ".foo.decryption.credentials[0].certificate-location=classpath:certificate-location",
            PREFIX + ".foo.singlelogout.url=https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SLOService.php",
            PREFIX + ".foo.singlelogout.response-url=https://simplesaml-for-spring-saml.cfapps.io/",
            PREFIX + ".foo.singlelogout.binding=post",
            PREFIX + ".foo.assertingparty.singlesignon.url=https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SSOService.php",
            PREFIX + ".foo.assertingparty.singlesignon.binding=post",
            PREFIX + ".foo.assertingparty.singlesignon.sign-request=false",
            PREFIX + ".foo.assertingparty.entity-id=https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/metadata.php",
            PREFIX + ".foo.assertingparty.verification.credentials[0].certificate-location=classpath:certificate-location",
            PREFIX + ".foo.asserting-party.singlelogout.url=https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SLOService.php",
            PREFIX + ".foo.asserting-party.singlelogout.response-url=https://simplesaml-for-spring-saml.cfapps.io/",
            PREFIX + ".foo.asserting-party.singlelogout.binding=post",
            PREFIX + ".foo.entity-id={baseUrl}/saml2/foo-entity-id",
            PREFIX + ".foo.acs.location={baseUrl}/login/saml2/foo-entity-id",
            PREFIX + ".foo.acs.binding=redirect" };

    private String[] getPropertyValues() {
        return PREFIX_VALUES;
    }
}
