public class test46 {

    @Test
    void getRegisteredClientsWhenValidParametersShouldAdapt() {
        OAuth2AuthorizationServerProperties.Client client = createClient();
        final String clientKey = "foo";
        this.properties.getClient().put(clientKey, client);
        List<RegisteredClient> registeredClients = this.mapper.asRegisteredClients();
        assertThat(registeredClients).hasSize(1);
        RegisteredClient registeredClient = registeredClients.get(0);
        assertThat(registeredClient.getClientId()).isEqualTo(clientKey);
        assertThat(registeredClient.getClientSecret()).isEqualTo("secret");
        assertThat(registeredClient.getClientAuthenticationMethods())
            .containsExactly(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        assertThat(registeredClient.getAuthorizationGrantTypes())
            .containsExactly(AuthorizationGrantType.AUTHORIZATION_CODE);
        assertThat(registeredClient.getRedirectUris()).containsExactly("https://example.com/redirect");
        assertThat(registeredClient.getPostLogoutRedirectUris()).containsExactly("https://example.com/logout");
        assertThat(registeredClient.getScopes()).containsExactly("user.read");
        assertThat(registeredClient.getClientSettings().isRequireProofKey()).isTrue();
        assertThat(registeredClient.getClientSettings().isRequireAuthorizationConsent()).isTrue();
        assertThat(registeredClient.getClientSettings().getJwkSetUrl()).isEqualTo("https://example.com/jwks");
        assertThat(registeredClient.getClientSettings().getTokenEndpointAuthenticationSigningAlgorithm())
            .isEqualTo(SignatureAlgorithm.RS256);
        assertThat(registeredClient.getTokenSettings().getAccessTokenFormat()).isEqualTo(OAuth2TokenFormat.REFERENCE);
        assertThat(registeredClient.getTokenSettings().getAccessTokenTimeToLive()).isEqualTo(Duration.ofSeconds(300));
        assertThat(registeredClient.getTokenSettings().getRefreshTokenTimeToLive()).isEqualTo(Duration.ofHours(24));
        assertThat(registeredClient.getTokenSettings().getDeviceCodeTimeToLive()).isEqualTo(Duration.ofMinutes(30));
        assertThat(registeredClient.getTokenSettings().isReuseRefreshTokens()).isEqualTo(true);
        assertThat(registeredClient.getTokenSettings().getIdTokenSignatureAlgorithm())
            .isEqualTo(SignatureAlgorithm.RS512);
    }
}
