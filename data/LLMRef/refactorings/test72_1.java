public class test72 {

    private void testIssuerConfiguration(OAuth2ClientProperties.Registration registration, String providerId,
    			int errorResponseCount, int numberOfRequests) throws Exception {
    		this.server = new MockWebServer();
    		this.server.start();
    		String issuer = this.server.url("").toString();
    		setupMockResponsesWithErrors(issuer, errorResponseCount);
    		OAuth2ClientProperties properties = new OAuth2ClientProperties();
    		Provider provider = new Provider();
    		provider.setIssuerUri(issuer);
    		properties.getProvider().put(providerId, provider);
    		properties.getRegistration().put("okta", registration);
    		Map<String, ClientRegistration> registrations = new OAuth2ClientPropertiesMapper(properties)
    			.asClientRegistrations();
    		ClientRegistration adapted = registrations.get("okta");
    		ProviderDetails providerDetails = adapted.getProviderDetails();
    		assertThat(adapted.getClientAuthenticationMethod()).isEqualTo(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
    		assertThat(adapted.getAuthorizationGrantType()).isEqualTo(AuthorizationGrantType.AUTHORIZATION_CODE);
    		assertThat(adapted.getRegistrationId()).isEqualTo("okta");
    		assertThat(adapted.getClientName()).isEqualTo(issuer);
    		assertThat(adapted.getScopes()).isNull();
    		assertThat(providerDetails.getAuthorizationUri()).isEqualTo("https://example.com/o/oauth2/v2/auth");
    		assertThat(providerDetails.getTokenUri()).isEqualTo("https://example.com/oauth2/v4/token");
    		assertThat(providerDetails.getJwkSetUri()).isEqualTo("https://example.com/oauth2/v3/certs");
    		UserInfoEndpoint userInfoEndpoint = providerDetails.getUserInfoEndpoint();
    		assertThat(userInfoEndpoint.getUri()).isEqualTo("https://example.com/oauth2/v3/userinfo");
    		assertThat(userInfoEndpoint.getAuthenticationMethod())
    			.isEqualTo(org.springframework.security.oauth2.core.AuthenticationMethod.HEADER);
    		assertThat(this.server.getRequestCount()).isEqualTo(numberOfRequests);
    	}
}
