public class test171 {

    @Test
    	void configureUriWithUsernameAndPasswordWhenUsernameAndPasswordPropertiesSet() {
    		this.contextRunner
    			.withPropertyValues("spring.elasticsearch.uris=http://user:password@localhost:9200,localhost:9201",
    					"spring.elasticsearch.username=admin", "spring.elasticsearch.password=admin")
    			.run((context) -> {
    				RestClient client = context.getBean(RestClient.class);
    				assertThat(client.getNodes().stream().map(Node::getHost).map(HttpHost::toString))
    					.containsExactly("http://localhost:9200", "http://localhost:9201");
    				assertThat(client)
    					.extracting("client.credentialsProvider", InstanceOfAssertFactories.type(CredentialsProvider.class))
    					.satisfies((credentialsProvider) -> {
    						Credentials uriCredentials = credentialsProvider
    							.getCredentials(new AuthScope("localhost", 9200));
    						assertThat(uriCredentials.getUserPrincipal().getName()).isEqualTo("user");
    						assertThat(uriCredentials.getPassword()).isEqualTo("password");
    						Credentials defaultCredentials = credentialsProvider
    							.getCredentials(new AuthScope("localhost", 9201));
    						assertThat(defaultCredentials.getUserPrincipal().getName()).isEqualTo("admin");
    						assertThat(defaultCredentials.getPassword()).isEqualTo("admin");
    					});
    			});
    	}

    private void assertNodeCredentials(CredentialsProvider credentialsProvider, String host, int port, String expectedUser, String expectedPassword) {
        Credentials uriCredentials = credentialsProvider.getCredentials(new AuthScope(host, port));
        assertThat(uriCredentials.getUserPrincipal().getName()).isEqualTo(expectedUser);
        assertThat(uriCredentials.getPassword()).isEqualTo(expectedPassword);
    }
}
