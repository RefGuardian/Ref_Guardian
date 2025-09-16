public class test44 {

    private static final String CLIENT_PREFIX = "client";

    @Test
    void registeredClientRepositoryBacksOffWhenRegisteredClientRepositoryBeanPresent() {
        this.contextRunner.withUserConfiguration(TestRegisteredClientRepositoryConfiguration.class)
            .withPropertyValues(CLIENT_PREFIX + ".foo.registration.client-id=abcd",
                    CLIENT_PREFIX + ".foo.registration.client-secret=secret",
                    CLIENT_PREFIX + ".foo.registration.client-authentication-methods=client_secret_basic",
                    CLIENT_PREFIX + ".foo.registration.authorization-grant-types=client_credentials",
                    CLIENT_PREFIX + ".foo.registration.scope=test")
            .run((context) -> {
                RegisteredClientRepository registeredClientRepository = context
                    .getBean(RegisteredClientRepository.class);
                RegisteredClient registeredClient = registeredClientRepository.findById("test");
                assertThat(registeredClient).isNotNull();
                assertThat(registeredClient.getClientId()).isEqualTo("abcd");
                assertThat(registeredClient.getClientSecret()).isEqualTo("secret");
                assertThat(registeredClient.getClientAuthenticationMethods())
                    .containsOnly(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
                assertThat(registeredClient.getAuthorizationGrantTypes())
                    .containsOnly(AuthorizationGrantType.CLIENT_CREDENTIALS);
                assertThat(registeredClient.getScopes()).containsOnly("test");
            });
    }
}
