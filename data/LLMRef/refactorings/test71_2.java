public class test71 {

    @Test
    void oidcProviderConfigurationWithCustomConfigurationOverridesProviderDefaults() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String issuer = this.server.url("").toString();
        setupMockResponse(issuer);
        OAuth2ClientProperties.Registration registration = createRegistration("okta-oidc");
        Provider provider = createProvider();
        provider.setIssuerUri(issuer);
        OAuth2ClientProperties properties = new OAuth2ClientProperties();
        properties.getProvider().put("okta-oidc", provider);
        properties.getRegistration().put("okta", registration);
        Map<String, ClientRegistration> registrations = new OAuth2ClientPropertiesMapper(properties)
                .asClientRegistrations();
        ClientRegistration adapted = registrations.get("okta");
        ProviderDetails providerDetails = adapted.getProviderDetails();
        assertThat(adapted.getClientAuthenticationMethod()).isEqualTo(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        assertThat(adapted.getAuthorizationGrantType()).isEqualTo(AuthorizationGrantType.AUTHORIZATION_CODE);
        assertThat(adapted.getRegistrationId()).isEqualTo("okta");
        assertThat(adapted.getClientName()).isEqualTo(issuer);
        assertThat(adapted.getScopes()).containsOnly("user");
        assertThat(adapted.getRedirectUri()).isEqualTo("https://example.com/redirect");
        assertThat(providerDetails.getAuthorizationUri()).isEqualTo("https://example.com/auth");
        assertThat(providerDetails.getTokenUri()).isEqualTo("https://example.com/token");
        assertThat(providerDetails.getJwkSetUri()).isEqualTo("https://example.com/jwk");
        UserInfoEndpoint userInfoEndpoint = providerDetails.getUserInfoEndpoint();
        assertThat(userInfoEndpoint.getUri()).isEqualTo("https://example.com/info");
        assertThat(userInfoEndpoint.getUserNameAttributeName()).isEqualTo("sub");
    }

    private void setupMockResponse(String issuer) {
        // implementation of the method
    }

    private Provider createProvider() {
        // implementation of the method
    }

    private OAuth2ClientProperties.Registration createRegistration(String registrationId) {
        // implementation of the method
    }
}
