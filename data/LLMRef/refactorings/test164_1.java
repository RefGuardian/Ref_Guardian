public class test164 {

    private static final String USERNAME = "user-1";
    private static final String PASSWORD = "password-1";
    private static final String MASTER_HOST = "master.redis.example.com";
    private static final String SENTINEL_USERNAME = "sentinel-1";
    private static final String SENTINEL_PASSWORD = "secret-1";

    @Bean
    RedisConnectionDetails redisConnectionDetails() {
        return new RedisConnectionDetails() {

            @Override
            public String getUsername() {
                return USERNAME;
            }

            @Override
            public String getPassword() {
                return PASSWORD;
            }

            @Override
            public Sentinel getSentinel() {
                return new Sentinel() {

                    @Override
                    public int getDatabase() {
                        return 1;
                    }

                    @Override
                    public String getMaster() {
                        return MASTER_HOST;
                    }

                    @Override
                    public List<Node> getNodes() {
                        return List.of(new Node("node-1", 12345));
                    }

                    @Override
                    public String getUsername() {
                        return SENTINEL_USERNAME;
                    }

                    @Override
                    public String getPassword() {
                        return SENTINEL_PASSWORD;
                    }

                };
            }

        };
    }
}
