public class test45 {

    @Test
    void authorizationServerSettingsBeanShouldBeCreatedWhenPropertiesPresent() {
        String issuer = PROPERTIES_PREFIX + ".issuer=https://example.com";
        String authorizationUri = PROPERTIES_PREFIX + ".endpoint.authorization-uri=/authorize";
        String deviceAuthorizationUri = PROPERTIES_PREFIX + ".endpoint.device-authorization-uri=/device_authorization";
        String deviceVerificationUri = PROPERTIES_PREFIX + ".endpoint.device-verification-uri=/device_verification";
        String tokenUri = PROPERTIES_PREFIX + ".endpoint.token-uri=/token";
        String jwkSetUri = PROPERTIES_PREFIX + ".endpoint.jwk-set-uri=/jwks";
        String tokenRevocationUri = PROPERTIES_PREFIX + ".endpoint.token-revocation-uri=/revoke";
        String tokenIntrospectionUri = PROPERTIES_PREFIX + ".endpoint.token-introspection-uri=/introspect";
        String oidcLogoutUri = PROPERTIES_PREFIX + ".endpoint.oidc.logout-uri=/logout";
        String oidcClientRegistrationUri = PROPERTIES_PREFIX + ".endpoint.oidc.client-registration-uri=/register";
        String oidcUserInfoUri = PROPERTIES_PREFIX + ".endpoint.oidc.user-info-uri=/user";

        this.contextRunner
                .withPropertyValues(issuer, authorizationUri, deviceAuthorizationUri, deviceVerificationUri, tokenUri, jwkSetUri,
                        tokenRevocationUri, tokenIntrospectionUri, oidcLogoutUri, oidcClientRegistrationUri, oidcUserInfoUri)
                .run((context) -> {
                    AuthorizationServerSettings settings = context.getBean(AuthorizationServerSettings.class);
                    assertThat(settings.getIssuer()).isEqualTo("https://example.com");
                    assertThat(settings.getAuthorizationEndpoint()).isEqualTo("/authorize");
                    assertThat(settings.getDeviceAuthorizationEndpoint()).isEqualTo("/device_authorization");
                    assertThat(settings.getDeviceVerificationEndpoint()).isEqualTo("/device_verification");
                    assertThat(settings.getTokenEndpoint()).isEqualTo("/token");
                    assertThat(settings.getJwkSetEndpoint()).isEqualTo("/jwks");
                    assertThat(settings.getTokenRevocationEndpoint()).isEqualTo("/revoke");
                    assertThat(settings.getTokenIntrospectionEndpoint()).isEqualTo("/introspect");
                    assertThat(settings.getOidcLogoutEndpoint()).isEqualTo("/logout");
                    assertThat(settings.getOidcClientRegistrationEndpoint()).isEqualTo("/register");
                    assertThat(settings.getOidcUserInfoEndpoint()).isEqualTo("/user");
                });
    }
}
