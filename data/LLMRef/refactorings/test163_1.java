public class test163 {

    public Standalone getStandalone() {
        return createStandaloneInstance();
    }

    private Standalone createStandaloneInstance() {
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
                return 16379;
            }

        };
    }
}
