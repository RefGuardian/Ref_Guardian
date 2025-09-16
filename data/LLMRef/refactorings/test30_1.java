public class test30 {

    private static final String USER = "username";
    private static final String PASS = "password";
    private static final String URL = "jdbcUrl";
    private static final String DRIVER = "driverClassName";

    @Override
    public LiquibaseConnectionDetails getConnectionDetails(JdbcConnectionDetails input) {
        return new LiquibaseConnectionDetails() {

            @Override
            public String getUsername() {
                return input.getUsername();
            }

            @Override
            public String getPassword() {
                return input.getPassword();
            }

            @Override
            public String getJdbcUrl() {
                return input.getJdbcUrl();
            }

            @Override
            public String getDriverClassName() {
                return input.getDriverClassName();
            }

        };
    }
}
