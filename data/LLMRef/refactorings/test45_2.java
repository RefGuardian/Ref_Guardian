public class test45 {

    private static final String PROPERTIES_PREFIX = "prefix";

    @Test
    void authorizationServerSettingsBeanShouldBeCreatedWhenPropertiesPresent() {
        this.contextRunner
                .withPropertyValues(PROPERTIES_PREFIX + ".issuer=https://example.com",
                        PROPERTIES_PREFIX + ".endpoint.authorization-uri=/authorize",
                        PROPERTIES_PREFIX + ".endpoint.device-authorization-uri=/device_authorization",
                        PROPERTIES_PREFIX + ".endpoint.device-verification-uri=/device_verification",
                        PROPERTIES_PREFIX + ".endpoint.token-uri=/token", PROPERTIES_PREFIX + ".endpoint.jwk-set-uri=/jwks",
                        PROPERTIES_PREFIX + ".endpoint.token-revocation-uri=/revoke",
                        PROPERTIES_PREFIX + ".endpoint.token-introspection-uri=/introspect",
                        PROPERTIES_PREFIX + ".endpoint.oidc.logout-uri=/logout",
                        PROPERTIES_PREFIX + ".endpoint.oidc.client-registration-uri=/register",
                        PROPERTIES_PREFIX + ".endpoint.oidc.user-info-uri=/user")
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
