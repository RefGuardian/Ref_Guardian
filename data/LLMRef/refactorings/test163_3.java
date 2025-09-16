public class test163 {

    private static final String REDIS_HOST = "redis.example.com";
    private static final int REDIS_PORT = 16379;
    private static final int DEFAULT_DATABASE = 1;

    @Override
    public Standalone getStandalone() {
        return new Standalone() {

            @Override
            public int getDatabase() {
                return DEFAULT_DATABASE;
            }

            @Override
            public String getHost() {
                return REDIS_HOST;
            }

            @Override
            public int getPort() {
                return REDIS_PORT;
            }

        };
    }
}
