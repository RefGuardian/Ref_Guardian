public class test180 {
	@Bean
	FlywayConnectionDetails flywayConnectionDetails() {
		return new FlywayConnectionDetails() {

			@Override
			public String getJdbcUrl() {
				return CONSTANT_JDBC_URL;
			}

			@Override
			public String getUsername() {
				return CONSTANT_USERNAME;
			}

			@Override
			public String getPassword() {
				return CONSTANT_PASSWORD;
			}

		};
	}

	private static final String CONSTANT_JDBC_URL = "jdbc:postgresql://database.example.com:12345/database-1";
	private static final String CONSTANT_USERNAME = "user-1";
	private static final String CONSTANT_PASSWORD = "secret-1";
}
