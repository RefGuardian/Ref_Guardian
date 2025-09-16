public class test48 {

    @Test
    void getAuthorizationServerSettingsWhenValidParametersShouldAdapt() {
        this.properties.setIssuer("https://example.com");
        OAuth2AuthorizationServerProperties.Endpoint endpoints = this.properties.getEndpoint();
        endpoints.setUriProperties(createEndPoints());
        AuthorizationServerSettings settings = this.mapper.asAuthorizationServerSettings();
        assertThat(settings.getIssuer()).isEqualTo("https://example.com");
        assertThat(settings.isMultipleIssuersAllowed()).isFalse();
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

    private OAuth2AuthorizationServerProperties.EndpointUriProperties createEndPoints() {
        OAuth2AuthorizationServerProperties.Endpoint endpoints = this.properties.getEndpoint();
        OAuth2AuthorizationServerProperties.EndpointUriProperties uriProperties = new OAuth2AuthorizationServerProperties.EndpointUriProperties();
        uriProperties.setAuthorizationUri("/authorize");
        uriProperties.setDeviceAuthorizationUri("/device_authorization");
        uriProperties.setDeviceVerificationUri("/device_verification");
        uriProperties.setTokenUri("/token");
        uriProperties.setJwkSetUri("/jwks");
        uriProperties.setTokenRevocationUri("/revoke");
        uriProperties.setTokenIntrospectionUri("/introspect");
        OAuth2AuthorizationServerProperties.OidcEndpoint oidc = endpoint.getOidc();
        uriProperties.setLogoutUri("/logout");
        uriProperties.setClientRegistrationUri("/register");
        uriProperties.setUserInfoUri("/user");
        return uriProperties;
    }
}
