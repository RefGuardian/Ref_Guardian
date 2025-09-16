public class test124 {

	@Test
	void shouldUseCustomConnectionDetailsWhenDefined() {
		this.contextRunner
			.withPropertyValues("spring.cassandra.contact-points=localhost:9042", "spring.cassandra.username=a-user",
					"spring.cassandra.password=a-password", "spring.cassandra.local-datacenter=some-datacenter")
			.withBean(CassandraConnectionDetails.class, this::cassandraConnectionDetails)
			.run(this::assertContext);
	}

	private void assertContext(ConfigurableApplicationContext context) {
		assertThat(context).hasSingleBean(DriverConfigLoader.class)
			.hasSingleBean(CassandraConnectionDetails.class)
			.doesNotHaveBean(PropertiesCassandraConnectionDetails.class);
		DriverExecutionProfile configuration = context.getBean(DriverConfigLoader.class)
			.getInitialConfig()
			.getDefaultProfile();
		assertThat(configuration.getStringList(DefaultDriverOption.CONTACT_POINTS))
			.containsOnly("cassandra.example.com:9042");
		assertThat(configuration.getString(DefaultDriverOption.AUTH_PROVIDER_USER_NAME)).isEqualTo("user-1");
		assertThat(configuration.getString(DefaultDriverOption.AUTH_PROVIDER_PASSWORD)).isEqualTo("secret-1");
		assertThat(configuration.getString(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER))
			.isEqualTo("datacenter-1");
	}

}
