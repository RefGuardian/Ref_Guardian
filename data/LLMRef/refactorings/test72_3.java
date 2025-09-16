public class test72 {

    private void testIssuerConfiguration(OAuth2ClientProperties.Registration registration, String providerId,
                int errorResponseCount, int numberOfRequests) throws Exception {
        setupMockServer();
        String issuer = getIssuerUrl();
        setupMockResponsesWithErrors(issuer, errorResponseCount);
        OAuth2ClientProperties properties = configureOAuth2ClientProperties(providerId, registration, issuer);
        Map<String, ClientRegistration> registrations = convertToClientRegistrations(properties);
        ClientRegistration adapted = registrations.get("okta");
        ProviderDetails providerDetails = adapted.getProviderDetails();
        assertClientRegistration(adapted, providerDetails, issuer, numberOfRequests);
    }

    private void setupMockServer() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
    }

    private String getIssuerUrl() {
        return this.server.url("").toString();
    }

    private OAuth2ClientProperties configureOAuth2ClientProperties(String providerId, OAuth2ClientProperties.Registration registration, String issuer) {
        OAuth2ClientProperties properties = new OAuth2ClientProperties();
        Provider provider = new Provider();
        provider.setIssuerUri(issuer);
        properties.getProvider().put(providerId, provider);
        properties.getRegistration().put("okta", registration);
        return properties;
    }

    private Map<String, ClientRegistration> convertToClientRegistrations(OAuth2ClientProperties properties) {
        return new OAuth2ClientPropertiesMapper(properties).asClientRegistrations();
    }

    private void assertClientRegistration(ClientRegistration adapted, ProviderDetails providerDetails, String issuer, int numberOfRequests) {
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
        assertThat(userInfoEndpoint.getAuthenticationMethod()).isEqualTo(org.springframework.security.oauth2.core.AuthenticationMethod.HEADER);
        assertThat(this.server.getRequestCount()).isEqualTo(numberOfRequests);
    }
}
