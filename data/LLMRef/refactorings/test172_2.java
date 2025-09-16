public class test172 {

    @Test
    void shouldUseCustomConnectionDetailsWhenDefined() {
        this.contextRunner.withUserConfiguration(ConnectionDetailsConfiguration.class).run((context) -> {
            assertThat(context).hasSingleBean(RestClient.class)
                .hasSingleBean(ElasticsearchConnectionDetails.class)
                .doesNotHaveBean(PropertiesElasticsearchConnectionDetails.class);
            RestClient restClient = context.getBean(RestClient.class);
            assertThat(restClient).hasFieldOrPropertyWithValue("pathPrefix", "/some-path");
            assertThat(restClient.getNodes().stream().map(Node::getHost).map(HttpHost::toString))
                .containsExactly("http://elastic.example.com:9200");
            assertThat(restClient)
                .extracting("client.credentialsProvider", InstanceOfAssertFactories.type(CredentialsProvider.class))
                .satisfies((credentialsProvider) -> {
                    Credentials uriCredentials = credentialsProvider
                        .getCredentials(new AuthScope("any.elastic.example.com", 80));
                    assertThat(uriCredentials.getUserPrincipal().getName()).isEqualTo("user-1");
                    assertThat(uriCredentials.getPassword()).isEqualTo("password-1");
                })
                .satisfies((credentialsProvider) -> {
                    Credentials uriCredentials = credentialsProvider
                        .getCredentials(new AuthScope("elastic.example.com", 9200));
                    assertThat(uriCredentials.getUserPrincipal().getName()).isEqualTo("node-user-1");
                    assertThat(uriCredentials.getPassword()).isEqualTo("node-password-1");
                });

        });
    }

}
