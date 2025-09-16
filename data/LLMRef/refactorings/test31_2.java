public class test31 {

    private static final FlywayConnectionDetails flywayConnectionDetails = new FlywayConnectionDetails() {

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

    @Override
    public FlywayConnectionDetails getConnectionDetails(JdbcConnectionDetails input) {
        return flywayConnectionDetails;
    }
}
