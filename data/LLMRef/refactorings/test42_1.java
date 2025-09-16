public class test42 {

    @Test
    void securityFilterChainsBackOffWhenSecurityFilterChainBeanPresent() {
        this.contextRunner
            .withUserConfiguration(TestSecurityFilterChainConfiguration.class,
                TestOAuth2AuthorizationServerConfiguration.class)
            .withPropertyValues(CLIENT_PREFIX + ".foo.registration.client-id=abcd",
                CLIENT_PREFIX + ".foo.registration.client-secret=secret",
                CLIENT_PREFIX + ".foo.registration.client-authentication-methods=client_secret_basic",
                CLIENT_PREFIX + ".foo.registration.authorization-grant-types=client_credentials",
                CLIENT_PREFIX + ".foo.registration.scopes=test")
            .run(this::assertTestContext);
    }
    
    private void assertTestContext(ConfigurableApplicationContext context) {
        assertThat(context).hasBean("authServerSecurityFilterChain");
        assertThat(context).doesNotHaveBean("authorizationServerSecurityFilterChain");
        assertThat(context).hasBean("securityFilterChain");
        assertThat(context).doesNotHaveBean("defaultSecurityFilterChain");
        assertThat(context).hasBean("registeredClientRepository");
        assertThat(context).hasBean("authorizationServerSettings");
        assertThat(findFilter(context, BearerTokenAuthenticationFilter.class, 0)).isNull();
        assertThat(findFilter(context, UsernamePasswordAuthenticationFilter.class, 1)).isNull();
    }
}
