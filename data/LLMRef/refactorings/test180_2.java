public class test180 {
    @Bean
    FlywayConnectionDetails flywayConnectionDetails() {
        return createFlywayConnectionDetails();
    }

    private FlywayConnectionDetails createFlywayConnectionDetails() {
        return new FlywayConnectionDetails() {

            @Override
            public String getJdbcUrl() {
                return "jdbc:postgresql://database.example.com:12345/database-1";
            }

            @Override
            public String getUsername() {
                return "user-1";
            }

            @Override
            public String getPassword() {
                return "secret-1";
            }

        };
    }
}
