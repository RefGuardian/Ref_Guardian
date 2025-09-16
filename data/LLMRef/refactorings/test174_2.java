public class test174 {

    private static final String USERNAME = "user-1";
    private static final String PASSWORD = "password-1";
    private static final String PATH_PREFIX = "/some-path";

    @Bean
    ElasticsearchConnectionDetails elasticsearchConnectionDetails() {
        return new ElasticsearchConnectionDetails() {
            @Override
            public List<Node> getNodes() {
                return List
                        .of(new Node("elastic.example.com", 9200, Protocol.HTTP, "node-user-1", "node-password-1"));
            }

            @Override
            public String getUsername() {
                return USERNAME;
            }

            @Override
            public String getPassword() {
                return PASSWORD;
            }

            @Override
            public String getPathPrefix() {
                return PATH_PREFIX;
            }

        };
    }
}
