public class test70 {

    @Test
	void test70() {
		OAuth2ClientProperties properties = new OAuth2ClientProperties();
		OAuth2ClientProperties.Registration registration = createRegistration();
		properties.getRegistration().put(createRegistrationId(), registration);
		Map<String, ClientRegistration> registrations = createClientRegistrations(properties);
		ClientRegistration adapted = registrations.get(createProviderId());
		ProviderDetails adaptedProvider = adapted.getProviderDetails();
		assertThat(adaptedProvider.getAuthorizationUri()).isEqualTo("https://accounts.google.com/o/oauth2/v2/auth");
		assertThat(adaptedProvider.getTokenUri()).isEqualTo("https://www.googleapis.com/oauth2/v4/token");
		UserInfoEndpoint userInfoEndpoint = adaptedProvider.getUserInfoEndpoint();
		assertThat(userInfoEndpoint.getUri()).isEqualTo("https://www.googleapis.com/oauth2/v3/userinfo");
		assertThat(userInfoEndpoint.getAuthenticationMethod())
			.isEqualTo(org.springframework.security.oauth2.core.AuthenticationMethod.HEADER);
		assertThat(adaptedProvider.getJwkSetUri()).isEqualTo("https://www.googleapis.com/oauth2/v3/certs");
		assertThat(adapted.getRegistrationId()).isEqualTo("google");
		assertThat(adapted.getClientId()).isEqualTo("clientId");
		assertThat(adapted.getClientSecret()).isEqualTo("clientSecret");
		assertThat(adapted.getClientAuthenticationMethod())
			.isEqualTo(org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
		assertThat(adapted.getAuthorizationGrantType())
			.isEqualTo(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE);
		assertThat(adapted.getRedirectUri()).isEqualTo("{baseUrl}/{action}/oauth2/code/{registrationId}");
		assertThat(adapted.getScopes()).containsExactly("openid", "profile", "email");
		assertThat(adapted.getClientName()).isEqualTo("Google");
	}

	private String createProviderId() {
		return "google";
	}

	private Map<String, ClientRegistration> createClientRegistrations(OAuth2ClientProperties properties) {
		return new OAuth2ClientPropertiesMapper(properties).asClientRegistrations();
	}

	private String createRegistrationId() {
		return "google";
	}

	private OAuth2ClientProperties.Registration createRegistration() {
		OAuth2ClientProperties.Registration registration = new OAuth2ClientProperties.Registration();
		registration.setClientId("clientId");
		registration.setClientSecret("clientSecret");
		return registration;
	}
}
