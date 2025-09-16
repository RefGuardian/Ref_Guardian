public class test164 {

    private static final String USERNAME_1 = "user-1";
    private static final String PASSWORD_1 = "password-1";
    private static final String MASTER_REDIS = "master.redis.example.com";
    private static final String SENTINEL_1 = "sentinel-1";
    private static final String SECRET_1 = "secret-1";

    @Bean
    RedisConnectionDetails redisConnectionDetails() {
        return new RedisConnectionDetails() {

            @Override
            public String getUsername() {
                return USERNAME_1;
            }

            @Override
            public String getPassword() {
                return PASSWORD_1;
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
                        return MASTER_REDIS;
                    }

                    @Override
                    public List<Node> getNodes() {
                        return List.of(new Node("node-1", 12345));
                    }

                    @Override
                    public String getUsername() {
                        return SENTINEL_1;
                    }

                    @Override
                    public String getPassword() {
                        return SECRET_1;
                    }

                };
            }

        };
    }
}
