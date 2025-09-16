public class test71 {

    @Test
    void oidcProviderConfigurationWithCustomConfigurationOverridesProviderDefaults() throws Exception {
        this.setupMockServer();
        String issuer = this.getIssuerUri();
        OAuth2ClientProperties properties = this.createOAuthClientProperties("okta-oidc", issuer, "okta");
        Map<String, ClientRegistration> registrations = this.mapPropertiesToClientRegistrations(properties);
        ClientRegistration adapted = this.getAdaptedClientRegistration(registrations, "okta");
        ProviderDetails providerDetails = adapted.getProviderDetails();
        this.assertClientRegistration(adapted);
        this.assertProviderDetails(providerDetails);
        this.assertUserInfoEndpoint(providerDetails.getUserInfoEndpoint());
    }

    private void setupMockServer() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String issuer = this.server.url("").toString();
        this.setupMockResponse(issuer);
    }

    private String getIssuerUri() {
        return this.server.url("").toString();
    }

    private OAuth2ClientProperties createOAuthClientProperties(String providerName, String issuer, String registrationName) {
        OAuth2ClientProperties properties = new OAuth2ClientProperties();
        OAuth2ClientProperties.Registration registration = this.createRegistration(registrationName);
        Provider provider = this.createProvider();
        provider.setIssuerUri(issuer);
        properties.getProvider().put(providerName, provider);
        properties.getRegistration().put(registrationName, registration);
        return properties;
    }

    private Map<String, ClientRegistration> mapPropertiesToClientRegistrations(OAuth2ClientProperties properties) {
        return new OAuth2ClientPropertiesMapper(properties).asClientRegistrations();
    }

    private ClientRegistration getAdaptedClientRegistration(Map<String, ClientRegistration> registrations, String registrationId) {
        return registrations.get(registrationId);
    }

    private void assertClientRegistration(ClientRegistration adapted) {
        assertThat(adapted.getClientAuthenticationMethod()).isEqualTo(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        assertThat(adapted.getAuthorizationGrantType()).isEqualTo(AuthorizationGrantType.AUTHORIZATION_CODE);
        assertThat(adapted.getRegistrationId()).isEqualTo("okta");
        assertThat(adapted.getClientName()).isEqualTo(issuer);
        assertThat(adapted.getScopes()).containsOnly("user");
        assertThat(adapted.getRedirectUri()).isEqualTo("https://example.com/redirect");
    }

    private void assertProviderDetails(ProviderDetails providerDetails) {
        assertThat(providerDetails.getAuthorizationUri()).isEqualTo("https://example.com/auth");
        assertThat(providerDetails.getTokenUri()).isEqualTo("https://example.com/token");
        assertThat(providerDetails.getJwkSetUri()).isEqualTo("https://example.com/jwk");
    }

    private void assertUserInfoEndpoint(UserInfoEndpoint userInfoEndpoint) {
        assertThat(userInfoEndpoint.getUri()).isEqualTo("https://example.com/info");
        assertThat(userInfoEndpoint.getUserNameAttributeName()).isEqualTo("sub");
    }
}
