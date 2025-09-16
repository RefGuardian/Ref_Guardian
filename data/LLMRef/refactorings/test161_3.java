public class test161 {

    @Bean
    RedisConnectionDetails redisConnectionDetails() {
        return createRedisConnectionDetails();
    }

    private RedisConnectionDetails createRedisConnectionDetails() {
        return new RedisConnectionDetails() {
    
            @Override
            public Standalone getStandalone() {
                return createStandalone();
            }

            private Standalone createStandalone() {
                return new Standalone() {
    
                    @Override
                    public String getHost() {
                        return "localhost";
                    }
    
                    @Override
                    public int getPort() {
                        return 6379;
                    }
    
                };
            }
    
        };
    }
}
