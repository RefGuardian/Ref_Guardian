public class test31 {

    private static final JdbcConnectionDetails input = new JdbcConnectionDetails();

    @Override
    public FlywayConnectionDetails getConnectionDetails() {
        return new FlywayConnectionDetails() {

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
