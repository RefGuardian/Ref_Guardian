public class test152 {

    private static final String JDBC_URL = "jdbc:postgresql://database.example.com:12345/database-1";
    private static final String USERNAME = "user-1";
    private static final String PASSWORD = "secret-1";

    @Bean
    LiquibaseConnectionDetails liquibaseConnectionDetails() {
        return new LiquibaseConnectionDetails() {

            @Override
            public String getJdbcUrl() {
                return JDBC_URL;
            }

            @Override
            public String getUsername() {
                return USERNAME;
            }

            @Override
            public String getPassword() {
                return PASSWORD;
            }

        };
    }
}
