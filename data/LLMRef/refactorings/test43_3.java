public class test43 {

    @Test
    	void registeredClientRepositoryBeanShouldBeCreatedWhenPropertiesPresent() {
    		this.contextRunner
    			.withPropertyValues(CLIENT_PREFIX + ".foo.registration.client-id=abcd",
    					CLIENT_PREFIX + ".foo.registration.client-secret=secret",
    					CLIENT_PREFIX + ".foo.registration.client-authentication-methods=client_secret_basic",
    					CLIENT_PREFIX + ".foo.registration.authorization-grant-types=client_credentials",
    					CLIENT_PREFIX + ".foo.registration.scopes=test")
    			.run((context) -> {
    				createAndVerifyRegisteredClient(context, "foo", "abcd", "secret", ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
        					AuthorizationGrantType.CLIENT_CREDENTIALS, "test");
    			});
    	}
    
    private void createAndVerifyRegisteredClient(ConfigurableApplicationContext context, String id, String clientId, String clientSecret,
    		ClientAuthenticationMethod authenticationMethod, AuthorizationGrantType grantType, String scope) {
    	RegisteredClientRepository registeredClientRepository = context.getBean(RegisteredClientRepository.class);
    	RegisteredClient registeredClient = registeredClientRepository.findById(id);
    	assertThat(registeredClient).isNotNull();
    	assertThat(registeredClient.getClientId()).isEqualTo(clientId);
    	assertThat(registeredClient.getClientSecret()).isEqualTo(clientSecret);
    	assertThat(registeredClient.getClientAuthenticationMethods()).containsOnly(authenticationMethod);
    	assertThat(registeredClient.getAuthorizationGrantTypes()).containsOnly(grantType);
    	assertThat(registeredClient.getScopes()).containsOnly(scope);
    }
}
