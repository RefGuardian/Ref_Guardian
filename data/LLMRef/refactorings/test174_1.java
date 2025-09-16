public class test174 {

    private static final String SOME_PATH = "/some-path";

    @Bean
    ElasticsearchConnectionDetails elasticsearchConnectionDetails() {
        return new ElasticsearchConnectionDetails() {

            private static final String NODE_USER_1 = "node-user-1";
            private static final String NODE_PASSWORD_1 = "node-password-1";

            @Override
            public List<Node> getNodes() {
                return List
                    .of(new Node("elastic.example.com", 9200, Protocol.HTTP, NODE_USER_1, NODE_PASSWORD_1));
            }

            @Override
            public String getUsername() {
                return "user-1";
            }

            @Override
            public String getPassword() {
                return "password-1";
            }

            @Override
            public String getPathPrefix() {
                return SOME_PATH;
            }

        };
    }
}
