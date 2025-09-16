public class test190 {

    private RegisteredClient getRegisteredClient(String registrationId, Client client) {
        Registration registration = client.getRegistration();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        RegisteredClient.Builder builder = RegisteredClient.withId(registrationId);

        map.from(registration::getClientId).to(builder::clientId);
        map.from(registration::getClientSecret).to(builder::clientSecret);
        map.from(registration::getClientName).to(builder::clientName);

        registration.getClientAuthenticationMethods().forEach(this::extractClientAuthenticationMethod);
        registration.getAuthorizationGrantTypes().forEach(this::extractAuthorizationGrantType);
        registration.getRedirectUris().forEach(this::extractRedirectUri);
        registration.getPostLogoutRedirectUris().forEach(this::extractPostLogoutRedirectUri);
        registration.getScopes().forEach(this::extractScope);

        builder.clientSettings(getClientSettings(client, map));
        builder.tokenSettings(getTokenSettings(client, map));
        return builder.build();
    }

    private void extractClientAuthenticationMethod(String clientAuthenticationMethod) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(clientAuthenticationMethod)
            .as(ClientAuthenticationMethod::new)
            .to(RegisteredClient.Builder::clientAuthenticationMethod);
    }

    private void extractAuthorizationGrantType(String authorizationGrantType) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(authorizationGrantType)
            .as(AuthorizationGrantType::new)
            .to(RegisteredClient.Builder::authorizationGrantType);
    }

    private void extractRedirectUri(String redirectUri) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(redirectUri).to(RegisteredClient.Builder::redirectUri);
    }

    private void extractPostLogoutRedirectUri(String redirectUri) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(redirectUri).to(RegisteredClient.Builder::postLogoutRedirectUri);
    }

    private void extractScope(String scope) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(scope).to(RegisteredClient.Builder::scope);
    }

}
