public class test165 {

    private static final String MASTER_REDIS_EXAMPLE_COM = "master.redis.example.com";

    @Override
    public Sentinel getSentinel() {
        return new Sentinel() {

            @Override
            public int getDatabase() {
                return 1;
            }

            @Override
            public String getMaster() {
                return MASTER_REDIS_EXAMPLE_COM;
            }

            @Override
            public List<Node> getNodes() {
                return List.of(new Node("node-1", 12345));
            }

            @Override
            public String getUsername() {
                return "sentinel-1";
            }

            @Override
            public String getPassword() {
                return "secret-1";
            }

        };
    }
}
