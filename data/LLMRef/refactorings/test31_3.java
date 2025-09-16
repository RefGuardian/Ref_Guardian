public class test31 {

    @Override
    public FlywayConnectionDetails getConnectionDetails(JdbcConnectionDetails input) {
        return createFlywayConnectionDetails(input);
    }

    private FlywayConnectionDetails createFlywayConnectionDetails(JdbcConnectionDetails input) {
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
