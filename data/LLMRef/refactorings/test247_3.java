public class test247 {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final int DATABASE = 10;
    
    private static final SslBundle SSL_BUNDLE = new SslBundle();
    
    /**
     * Creates a new instance with the given host, port, database and SSL bundle.
     * @return the new instance
     * @since 3.5.0
     */
    static Standalone of() {
        Assert.hasLength(HOST, "'host' must not be empty");
        return new Standalone() {

            @Override
            public String getHost() {
                return HOST;
            }

            @Override
            public int getPort() {
                return PORT;
            }

            @Override
            public int getDatabase() {
                return DATABASE;
            }

            @Override
            public SslBundle getSslBundle() {
                return SSL_BUNDLE;
            }
        };
    }
}
