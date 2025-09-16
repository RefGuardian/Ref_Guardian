public class test190 {

    private RegisteredClient getRegisteredClient(String registrationId, Client client) {
        Registration registration = client.getRegistration();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        RegisteredClient.Builder builder = createRegisteredClientBuilder(registrationId, registration, map);
        return builder.build();
    }

    private RegisteredClient.Builder createRegisteredClientBuilder(String registrationId, Registration registration, PropertyMapper map) {
        RegisteredClient.Builder builder = RegisteredClient.withId(registrationId);
        map.from(registration::getClientId).to(builder::clientId);
        map.from(registration::getClientSecret).to(builder::clientSecret);
        map.from(registration::getClientName).to(builder::clientName);
        registration.getClientAuthenticationMethods()
                .forEach((clientAuthenticationMethod) -> map.from(clientAuthenticationMethod)
                        .as(ClientAuthenticationMethod::new)
                        .to(builder::clientAuthenticationMethod));
        registration.getAuthorizationGrantTypes()
                .forEach((authorizationGrantType) -> map.from(authorizationGrantType)
                        .as(AuthorizationGrantType::new)
                        .to(builder::authorizationGrantType));
        registration.getRedirectUris().forEach((redirectUri) -> map.from(redirectUri).to(builder::redirectUri));
        registration.getPostLogoutRedirectUris()
                .forEach((redirectUri) -> map.from(redirectUri).to(builder::postLogoutRedirectUri));
        registration.getScopes().forEach((scope) -> map.from(scope).to(builder::scope));
        builder.clientSettings(getClientSettings(client, map));
        builder.tokenSettings(getTokenSettings(client, map));
        return builder;
    }
}
