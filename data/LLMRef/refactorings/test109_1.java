public class test109 {

    @Test
	void dbcp2UsesCustomConnectionDetailsWhenDefined() {
		ApplicationContextRunner runner = createApplicationContextRunner();
		runner.run((context) -> {
			verifyConnectionDetails(context);
		});
	}

	private ApplicationContextRunner createApplicationContextRunner() {
		return new ApplicationContextRunner()
			.withPropertyValues("spring.datasource.type=org.apache.commons.dbcp2.BasicDataSource",
					"spring.datasource.dbcp2.url=jdbc:broken", "spring.datasource.dbcp2.username=alice",
					"spring.datasource.dbcp2.password=secret")
			.withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))
			.withBean(JdbcConnectionDetails.class, TestJdbcConnectionDetails::new);
	}

	private void verifyConnectionDetails(ApplicationContext context) {
		assertThat(context).hasSingleBean(JdbcConnectionDetails.class)
			.doesNotHaveBean(PropertiesJdbcConnectionDetails.class);
		DataSource dataSource = context.getBean(DataSource.class);
		assertThat(dataSource).asInstanceOf(InstanceOfAssertFactories.type(BasicDataSource.class))
			.satisfies((dbcp2) -> {
				assertThat(dbcp2.getUserName()).isEqualTo("user-1");
				assertThat(dbcp2).extracting("password").isEqualTo("password-1");
				assertThat(dbcp2.getDriverClassName()).isEqualTo(DatabaseDriver.POSTGRESQL.getDriverClassName());
				assertThat(dbcp2.getUrl()).isEqualTo("jdbc:customdb://customdb.example.com:12345/database-1");
			});
	}
}
