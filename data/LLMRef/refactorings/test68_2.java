public class test68 {

    @Test
    void getClientRegistrationsWhenUsingCommonProviderShouldAdapt() {
        OAuth2ClientProperties properties = createOAuth2ClientProperties();
        Map<String, ClientRegistration> registrations = mapOAuth2ClientPropertiesToClientRegistrations(properties);
        ClientRegistration adapted = getAdaptedRegistration(registrations);
        ProviderDetails adaptedProvider = adapted.getProviderDetails();
        assertAdaptedProviderDetails(adaptedProvider);
        assertAdaptedClientRegistration(adapted);
    }

    private OAuth2ClientProperties createOAuth2ClientProperties() {
        OAuth2ClientProperties properties = new OAuth2ClientProperties();
        OAuth2ClientProperties.Registration registration = createRegistration();
        properties.getRegistration().put("registration", registration);
        return properties;
    }

    private OAuth2ClientProperties.Registration createRegistration() {
        OAuth2ClientProperties.Registration registration = new OAuth2ClientProperties.Registration();
        registration.setProvider("google");
        registration.setClientId("clientId");
        registration.setClientSecret("clientSecret");
        return registration;
    }

    private Map<String, ClientRegistration> mapOAuth2ClientPropertiesToClientRegistrations(OAuth2ClientProperties properties) {
        return new OAuth2ClientPropertiesMapper(properties)
            .asClientRegistrations();
    }

    private ClientRegistration getAdaptedRegistration(Map<String, ClientRegistration> registrations) {
        return registrations.get("registration");
    }

    private void assertAdaptedProviderDetails(ProviderDetails adaptedProvider) {
        assertThat(adaptedProvider.getAuthorizationUri()).isEqualTo("https://accounts.google.com/o/oauth2/v2/auth");
        assertThat(adaptedProvider.getTokenUri()).isEqualTo("https://www.googleapis.com/oauth2/v4/token");
        UserInfoEndpoint userInfoEndpoint = adaptedProvider.getUserInfoEndpoint();
        assertThat(userInfoEndpoint.getUri()).isEqualTo("https://www.googleapis.com/oauth2/v3/userinfo");
        assertThat(userInfoEndpoint.getUserNameAttributeName()).isEqualTo(IdTokenClaimNames.SUB);
        assertThat(adaptedProvider.getJwkSetUri()).isEqualTo("https://www.googleapis.com/oauth2/v3/certs");
    }

    private void assertAdaptedClientRegistration(ClientRegistration adapted) {
        assertThat(adapted.getRegistrationId()).isEqualTo("registration");
        assertThat(adapted.getClientId()).isEqualTo("clientId");
        assertThat(adapted.getClientSecret()).isEqualTo("clientSecret");
        assertThat(adapted.getClientAuthenticationMethod())
            .isEqualTo(org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        assertThat(adapted.getAuthorizationGrantType())
            .isEqualTo(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE);
        assertThat(adapted.getRedirectUri()).isEqualTo("{baseUrl}/{action}/oauth2/code/{registrationId}");
        assertThat(adapted.getScopes()).containsExactly("openid", "profile", "email");
        assertThat(adapted.getClientName()).isEqualTo("Google");
    }
}
