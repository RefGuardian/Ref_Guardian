public class test162 {

    private static final String USERNAME = "user-1";
    private static final String PASSWORD = "password-1";
    private static final String HOST = "redis.example.com";
    private static final int DATABASE = 1;
    private static final int PORT = 16379;

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
            public Standalone getStandalone() {
                return new Standalone() {

                    @Override
                    public int getDatabase() {
                        return DATABASE;
                    }

                    @Override
                    public String getHost() {
                        return HOST;
                    }

                    @Override
                    public int getPort() {
                        return PORT;
                    }

                };
            }

        };
    }
}
