public class test174 {

    private static final String DEFAULT_USERNAME = "user-1";
    private static final String DEFAULT_PASSWORD = "password-1";

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
                    return DEFAULT_USERNAME;
                }

                @Override
                public String getPassword() {
                    return DEFAULT_PASSWORD;
                }

                @Override
                public String getPathPrefix() {
                    return "/some-path";
                }

            };
        }
}
