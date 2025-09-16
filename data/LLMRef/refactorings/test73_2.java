public class test73 {

    @Test
    @WithPackageResources({ "certificate-location", "private-key-location" })
    void relyingPartyRegistrationRepositoryBeanShouldBeCreatedWhenPropertiesPresent() {
        this.contextRunner.withPropertyValues(getPropertyValues()).run((context) -> {
            RelyingPartyRegistrationRepository repository = context.getBean(RelyingPartyRegistrationRepository.class);
            RelyingPartyRegistration registration = repository.findByRegistrationId("foo");

            assertThat(getSsoServiceLocation(registration)).isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SSOService.php");
            assertThat(getEntityId(registration)).isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/metadata.php");
            assertThat(registration.getAssertionConsumerServiceLocation())
                .isEqualTo("{baseUrl}/login/saml2/foo-entity-id");
            assertThat(registration.getAssertionConsumerServiceBinding()).isEqualTo(Saml2MessageBinding.REDIRECT);
            assertThat(getSsoServiceBinding(registration)).isEqualTo(Saml2MessageBinding.POST);
            assertThat(registration.getAssertingPartyMetadata().getWantAuthnRequestsSigned()).isFalse();
            assertThat(registration.getSigningX509Credentials()).hasSize(1);
            assertThat(registration.getDecryptionX509Credentials()).hasSize(1);
            assertThat(getVerificationX509Credentials(registration)).isNotNull();
            assertThat(getEntityId(registration)).isEqualTo("{baseUrl}/saml2/foo-entity-id");
            assertThat(getSingleLogoutServiceLocation(registration))
                .isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SLOService.php");
            assertThat(getSingleLogoutServiceResponseLocation(registration))
                .isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/");
            assertThat(registration.getSingleLogoutServiceBinding()).isEqualTo(Saml2MessageBinding.POST);
            assertThat(getSingleLogoutServiceLocation(registration))
                .isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SLOService.php");
            assertThat(getSingleLogoutServiceResponseLocation(registration))
                .isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/");
            assertThat(getSingleLogoutServiceBinding(registration))
                .isEqualTo(Saml2MessageBinding.POST);
        });
    }

    private String getSsoServiceLocation(RelyingPartyRegistration registration) {
        return registration.getAssertingPartyMetadata().getSingleSignOnServiceLocation();
    }

    private String getEntityId(RelyingPartyRegistration registration) {
        return registration.getAssertingPartyMetadata().getEntityId();
    }

    private Saml2MessageBinding getSsoServiceBinding(RelyingPartyRegistration registration) {
        return registration.getAssertingPartyMetadata().getSingleSignOnServiceBinding();
    }

    private X509VerificationCredentials getVerificationX509Credentials(RelyingPartyRegistration registration) {
        return registration.getAssertingPartyMetadata().getVerificationX509Credentials();
    }

    private String getSingleLogoutServiceLocation(RelyingPartyRegistration registration) {
        return registration.getAssertingPartyMetadata().getSingleLogoutServiceLocation();
    }

    private String getSingleLogoutServiceResponseLocation(RelyingPartyRegistration registration) {
        return registration.getAssertingPartyMetadata().getSingleLogoutServiceResponseLocation();
    }

    private Saml2MessageBinding getSingleLogoutServiceBinding(RelyingPartyRegistration registration) {
        return registration.getAssertingPartyMetadata().getSingleLogoutServiceBinding();
    }
}
