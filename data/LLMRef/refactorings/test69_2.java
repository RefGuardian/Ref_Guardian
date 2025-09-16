public class test69 {

    @Test
    void getClientRegistrationsWhenUsingCommonProviderWithOverrideShouldAdapt() {
        OAuth2ClientProperties properties = new OAuth2ClientProperties();
        OAuth2ClientProperties.Registration registration = createRegistration("google");
        registration.setClientName("clientName");
        properties.getRegistration().put("registration", registration);
        Map<String, ClientRegistration> registrations = new OAuth2ClientPropertiesMapper(properties)
                .asClientRegistrations();
        ClientRegistration adapted = registrations.get("registration");
        ProviderDetails adaptedProvider = adapted.getProviderDetails();
        assertThat(adaptedProvider.getAuthorizationUri()).isEqualTo("https://accounts.google.com/o/oauth2/v2/auth");
        assertThat(adaptedProvider.getTokenUri()).isEqualTo("https://www.googleapis.com/oauth2/v4/token");
        UserInfoEndpoint userInfoEndpoint = adaptedProvider.getUserInfoEndpoint();
        assertThat(userInfoEndpoint.getUri()).isEqualTo("https://www.googleapis.com/oauth2/v3/userinfo");
        assertThat(userInfoEndpoint.getUserNameAttributeName()).isEqualTo(IdTokenClaimNames.SUB);
        assertThat(userInfoEndpoint.getAuthenticationMethod())
                .isEqualTo(ClientAuthenticationMethod.HEADER);
        assertThat(adaptedProvider.getJwkSetUri()).isEqualTo("https://www.googleapis.com/oauth2/v3/certs");
        assertThat(adapted.getRegistrationId()).isEqualTo("registration");
        assertThat(adapted.getClientId()).isEqualTo("clientId");
        assertThat(adapted.getClientSecret()).isEqualTo("clientSecret");
        assertThat(adapted.getClientAuthenticationMethod())
                .isEqualTo(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        assertThat(adapted.getAuthorizationGrantType())
                .isEqualTo(AuthorizationGrantType.AUTHORIZATION_CODE);
        assertThat(adapted.getRedirectUri()).isEqualTo("https://example.com/redirect");
        assertThat(adapted.getScopes()).containsExactly("user");
        assertThat(adapted.getClientName()).isEqualTo("clientName");
    }
}
