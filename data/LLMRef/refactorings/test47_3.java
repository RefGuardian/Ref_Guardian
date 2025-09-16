public class test47 {

    private OAuth2AuthorizationServerProperties.Client createClient() {
        OAuth2AuthorizationServerProperties.Client client = new OAuth2AuthorizationServerProperties.Client();
        setClientFields(client);
        setRegistrationFields(client.getRegistration());
        setTokenFields(client.getToken());
        return client;
    }

    private void setClientFields(OAuth2AuthorizationServerProperties.Client client) {
        client.setRequireProofKey(true);
        client.setRequireAuthorizationConsent(true);
        client.setJwkSetUri("https://example.com/jwks");
        client.setTokenEndpointAuthenticationSigningAlgorithm("rs256");
    }

    private void setRegistrationFields(OAuth2AuthorizationServerProperties.Registration registration) {
        registration.setClientId("foo");
        registration.setClientSecret("secret");
        registration.getClientAuthenticationMethods().add("client_secret_basic");
        registration.getAuthorizationGrantTypes().add("authorization_code");
        registration.getRedirectUris().add("https://example.com/redirect");
        registration.getPostLogoutRedirectUris().add("https://example.com/logout");
        registration.getScopes().add("user.read");
    }

    private void setTokenFields(OAuth2AuthorizationServerProperties.Token token) {
        token.setAccessTokenFormat("reference");
        token.setAccessTokenTimeToLive(Duration.ofSeconds(300));
        token.setRefreshTokenTimeToLive(Duration.ofHours(24));
        token.setDeviceCodeTimeToLive(Duration.ofMinutes(30));
        token.setReuseRefreshTokens(true);
        token.setIdTokenSignatureAlgorithm("rs512");
    }
}
