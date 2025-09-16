public class test166 {

    @Bean
    RedisConnectionDetails redisConnectionDetails() {
        return createDetails();
    }

    private RedisConnectionDetails createDetails() {
        return new RedisConnectionDetails() {

            @Override
            public String getUsername() {
                return "user-1";
            }

            @Override
            public String getPassword() {
                return "password-1";
            }

            @Override
            public Cluster getCluster() {
                return new Cluster() {

                    @Override
                    public List<Node> getNodes() {
                        return List.of(new Node("node-1", 12345), new Node("node-2", 23456));
                    }

                };
            }

        };
    }
}
