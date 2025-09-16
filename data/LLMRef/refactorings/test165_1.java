public class test165 {

    private static final int DATABASE_NUMBER = 1;
    private static final String MASTER_URL = "master.redis.example.com";
    private static final String SENTINEL_USERNAME = "sentinel-1";
    private static final String SENTINEL_PASSWORD = "secret-1";

    @Override
    public Sentinel getSentinel() {
        return new Sentinel() {

            @Override
            public int getDatabase() {
                return DATABASE_NUMBER;
            }

            @Override
            public String getMaster() {
                return MASTER_URL;
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
}
