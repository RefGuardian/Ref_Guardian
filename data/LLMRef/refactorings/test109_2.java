public class test109 {

    @Test
    void dbcp2UsesCustomConnectionDetailsWhenDefined() {
        final String dataSourceType = "org.apache.commons.dbcp2.BasicDataSource";
        final String url = "jdbc:broken";
        final String username = "alice";
        final String password = "secret";

        ApplicationContextRunner runner = new ApplicationContextRunner()
            .withPropertyValues("spring.datasource.type=" + dataSourceType,
                                 "spring.datasource.dbcp2.url=" + url,
                                 "spring.datasource.dbcp2.username=" + username,
                                 "spring.datasource.dbcp2.password=" + password)
            .withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))
            .withBean(JdbcConnectionDetails.class, TestJdbcConnectionDetails::new);
        runner.run((context) -> {
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
        });
    }
}
