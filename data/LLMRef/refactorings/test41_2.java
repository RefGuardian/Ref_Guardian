public class test41 {

    @Test
    void webSecurityConfigurationConfiguresAuthorizationServerWithFormLogin() {
        final String clientId = CLIENT_PREFIX + ".foo.registration.client-id=abcd";
        final String clientSecret = CLIENT_PREFIX + ".foo.registration.client-secret=secret";
        final String clientAuthenticationMethods = CLIENT_PREFIX + ".foo.registration.client-authentication-methods=client_secret_basic";
        final String authorizationGrantTypes = CLIENT_PREFIX + ".foo.registration.authorization-grant-types=client_credentials";
        final String scopes = CLIENT_PREFIX + ".foo.registration.scopes=test";

        this.contextRunner.withUserConfiguration(TestOAuth2AuthorizationServerConfiguration.class)
                .withPropertyValues(clientId, clientSecret, clientAuthenticationMethods, authorizationGrantTypes, scopes)
                .run((context) -> {
                    assertThat(context).hasBean("authorizationServerSecurityFilterChain");
                    assertThat(context).hasBean("defaultSecurityFilterChain");
                    assertThat(context).hasBean("registeredClientRepository");
                    assertThat(context).hasBean("authorizationServerSettings");
                    assertThat(findFilter(context, OAuth2AuthorizationEndpointFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, OAuth2TokenEndpointFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, OAuth2TokenIntrospectionEndpointFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, OAuth2TokenRevocationEndpointFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, OAuth2AuthorizationServerMetadataEndpointFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, OidcProviderConfigurationEndpointFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, OidcUserInfoEndpointFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, BearerTokenAuthenticationFilter.class, 0)).isNotNull();
                    assertThat(findFilter(context, OidcClientRegistrationEndpointFilter.class, 0)).isNull();
                    assertThat(findFilter(context, UsernamePasswordAuthenticationFilter.class, 0)).isNull();
                    assertThat(findFilter(context, DefaultLoginPageGeneratingFilter.class, 1)).isNotNull();
                    assertThat(findFilter(context, UsernamePasswordAuthenticationFilter.class, 1)).isNotNull();
                });
    }
}
