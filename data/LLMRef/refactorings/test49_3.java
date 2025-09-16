public class test49 {

    @Test
    void getAuthorizationServerSettingsWhenMultipleIssuersAllowedShouldAdapt() {
        setMultipleIssuersAllowed();
        OAuth2AuthorizationServerProperties.Endpoint endpoints = this.properties.getEndpoint();
        endpoints.setAuthorizationUri("/authorize");
        endpoints.setDeviceAuthorizationUri("/device_authorization");
        endpoints.setDeviceVerificationUri("/device_verification");
        endpoints.setTokenUri("/token");
        endpoints.setJwkSetUri("/jwks");
        endpoints.setTokenRevocationUri("/revoke");
        endpoints.setTokenIntrospectionUri("/introspect");
        OAuth2AuthorizationServerProperties.OidcEndpoint oidc = endpoints.getOidc();
        oidc.setLogoutUri("/logout");
        oidc.setClientRegistrationUri("/register");
        oidc.setUserInfoUri("/user");
        AuthorizationServerSettings settings = this.mapper.asAuthorizationServerSettings();
        assertThat(settings.getIssuer()).isNull();
        assertThat(settings.isMultipleIssuersAllowed()).isTrue();
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
    }
    
    private void setMultipleIssuersAllowed() {
        this.properties.setMultipleIssuersAllowed(true);
    }
}
