public class test73 {

    @Test
    	@WithPackageResources({ "certificate-location", "private-key-location" })
    	void relyingPartyRegistrationRepositoryBeanShouldBeCreatedWhenPropertiesPresent() {
    		this.contextRunner.withPropertyValues(getPropertyValues()).run((context) -> {
    			RelyingPartyRegistrationRepository repository = context.getBean(RelyingPartyRegistrationRepository.class);
    			RelyingPartyRegistration registration = repository.findByRegistrationId("foo");
    
    			assertThat(registration.getAssertingPartyMetadata().getSingleSignOnServiceLocation())
    				.isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SSOService.php");
    			assertThat(registration.getAssertingPartyMetadata().getEntityId())
    				.isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/metadata.php");
    			assertThat(registration.getAssertionConsumerServiceLocation())
    				.isEqualTo("{baseUrl}/login/saml2/foo-entity-id");
    			assertThat(registration.getAssertionConsumerServiceBinding()).isEqualTo(Saml2MessageBinding.REDIRECT);
    			assertThat(registration.getAssertingPartyMetadata().getSingleSignOnServiceBinding())
    				.isEqualTo(Saml2MessageBinding.POST);
    			assertThat(registration.getAssertingPartyMetadata().getWantAuthnRequestsSigned()).isFalse();
    			assertThat(registration.getSigningX509Credentials()).hasSize(1);
    			assertThat(registration.getDecryptionX509Credentials()).hasSize(1);
    			assertThat(registration.getAssertingPartyMetadata().getVerificationX509Credentials()).isNotNull();
    			assertThat(registration.getEntityId()).isEqualTo("{baseUrl}/saml2/foo-entity-id");
    			assertThat(registration.getSingleLogoutServiceLocation())
    				.isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SLOService.php");
    			assertThat(registration.getSingleLogoutServiceResponseLocation())
    				.isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/");
    			assertThat(registration.getSingleLogoutServiceBinding()).isEqualTo(Saml2MessageBinding.POST);
    			assertThat(registration.getAssertingPartyMetadata().getSingleLogoutServiceLocation())
    				.isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/saml2/idp/SLOService.php");
    			assertThat(registration.getAssertingPartyMetadata().getSingleLogoutServiceResponseLocation())
    				.isEqualTo("https://simplesaml-for-spring-saml.cfapps.io/");
    			assertThat(registration.getAssertingPartyMetadata().getSingleLogoutServiceBinding())
    				.isEqualTo(Saml2MessageBinding.POST);
    		});
    	}
}
