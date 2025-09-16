public class test179 {

    @Test
    	void flywayConnectionDetailsAreUsedOverFlywayProperties() {
    		this.contextRunner
    			.withUserConfiguration(EmbeddedDataSourceConfiguration.class, FlywayConnectionDetailsConfiguration.class,
    					MockFlywayMigrationStrategy.class)
    			.withPropertyValues("spring.flyway.url=jdbc:hsqldb:mem:flywaytest", "spring.flyway.user=some-user",
    					"spring.flyway.password=some-password",
    					"spring.flyway.driver-class-name=org.hsqldb.jdbc.JDBCDriver")
    			.run((context) -> {
    				assertThat(context).hasSingleBean(Flyway.class);
    				Flyway flyway = context.getBean(Flyway.class);
    				DataSource dataSource = flyway.getConfiguration().getDataSource();
    				assertThat(dataSource).isInstanceOf(SimpleDriverDataSource.class);
    				SimpleDriverDataSource simpleDriverDataSource = (SimpleDriverDataSource) dataSource;
    				assertThat(simpleDriverDataSource.getUrl())
    					.isEqualTo("jdbc:postgresql://database.example.com:12345/database-1");
    				assertThat(simpleDriverDataSource.getUsername()).isEqualTo("user-1");
    				assertThat(simpleDriverDataSource.getPassword()).isEqualTo("secret-1");
    				assertThat(simpleDriverDataSource.getDriver()).isInstanceOf(Driver.class);
    			});
    	}
}
