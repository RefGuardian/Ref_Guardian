public class test30 {

    private static final String DEFAULT_USERNAME = input.getUsername();
    private static final String DEFAULT_PASSWORD = input.getPassword();
    private static final String DEFAULT_JDBC_URL = input.getJdbcUrl();
    private static final String DEFAULT_DRIVER_CLASS_NAME = input.getDriverClassName();

    @Override
    public LiquibaseConnectionDetails getConnectionDetails(JdbcConnectionDetails input) {
        return new LiquibaseConnectionDetails() {

            @Override
            public String getUsername() {
                return DEFAULT_USERNAME;
            }

            @Override
            public String getPassword() {
                return DEFAULT_PASSWORD;
            }

            @Override
            public String getJdbcUrl() {
                return DEFAULT_JDBC_URL;
            }

            @Override
            public String getDriverClassName() {
                return DEFAULT_DRIVER_CLASS_NAME;
            }

        };
    }
}
