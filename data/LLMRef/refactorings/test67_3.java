public class test67 {

    @Test
    	void getClientRegistrationsWhenUsingDefinedProviderShouldAdapt() {
    		OAuth2ClientProperties properties = new OAuth2ClientProperties();
    		Provider provider = createProvider();
    	 provider.setUserInfoAuthenticationMethod("form");
    		OAuth2ClientProperties.Registration registration = createRegistration("provider");
    	 registration.setClientName("clientName");
    		properties.getRegistration().put("registration", registration);
    		properties.getProvider().put("provider", provider);
    		Map<String, ClientRegistration> registrations = new OAuth2ClientPropertiesMapper(properties)
    			.asClientRegistrations();
    		ClientRegistration adapted = registrations.get("registration");
    		ProviderDetails adaptedProvider = adapted.getProviderDetails();
    		assertThat(adaptedProvider.getAuthorizationUri()).isEqualTo("https://example.com/auth");
    		assertThat(adaptedProvider.getTokenUri()).isEqualTo("https://example.com/token");
    		UserInfoEndpoint userInfoEndpoint = adaptedProvider.getUserInfoEndpoint();
    		assertThat(userInfoEndpoint.getUri()).isEqualTo("https://example.com/info");
    		assertThat(userInfoEndpoint.getAuthenticationMethod())
                .isEqualTo(org.springframework.security.oauth2.core.AuthenticationMethod.FORM);
    		assertThat(userInfoEndpoint.getUserNameAttributeName()).isEqualTo("sub");
    		assertThat(adaptedProvider.getJwkSetUri()).isEqualTo("https://example.com/jwk");
    		assertThat(adapted.getRegistrationId()).isEqualTo("registration");
    		assertThat(adapted.getClientId()).isEqualTo("clientId");
    		assertThat(adapted.getClientSecret()).isEqualTo("clientSecret");
    		assertThat(adapted.getClientAuthenticationMethod())
                .isEqualTo(org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_POST);
    		assertThat(adapted.getAuthorizationGrantType())
                .isEqualTo(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE);
    		assertThat(adapted.getRedirectUri()).isEqualTo("https://example.com/redirect");
    		assertThat(adapted.getScopes()).containsExactly("user");
    		assertThat(adapted.getClientName()).isEqualTo("clientName");
    	}

    private Provider createProvider() {
     Provider provider = new Provider();
     provider.setAuthorizationUri("https://example.com/auth");
     provider.setTokenUri("https://example.com/token");
     provider.setUserInfoEndpoint(new UserInfoEndpoint("https://example.com/info", 
                  org.springframework.security.oauth2.core.AuthenticationMethod.FORM, "sub"));
     provider.setJwkSetUri("https://example.com/jwk");
     return provider;
    }

    private OAuth2ClientProperties.Registration createRegistration(String provider) {
     OAuth2ClientProperties.Registration registration = new OAuth2ClientProperties.Registration();
     registration.setClientId("clientId");
     registration.setClientSecret("clientSecret");
     registration.setClientAuthenticationMethod(org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_POST);
     registration.setAuthorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE);
     registration.setRedirectUri("https://example.com/redirect");
     registration.setScope(Collections.singletonList("user"));
     return registration;
    }
}
