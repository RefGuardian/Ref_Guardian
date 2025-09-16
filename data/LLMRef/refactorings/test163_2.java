public class test163 {

    private static final int STANDALONE_PORT = 16379;

    @Override
    public Standalone getStandalone() {
        return new Standalone() {

            @Override
            public int getDatabase() {
                return 1;
            }

            @Override
            public String getHost() {
                return "redis.example.com";
            }

            @Override
            public int getPort() {
                return STANDALONE_PORT;
            }

        };
    }
}
