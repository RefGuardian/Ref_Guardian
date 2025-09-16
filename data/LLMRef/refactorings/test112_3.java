public class test112 {

    private static final String USERNAME = "user-1";
    private static final String PASSWORD = "password-1";
    private static final String JDBC_URL = "jdbc:postgresql://postgres.example.com:12345/database-1";

    @Test
    void shouldUseCustomConnectionDetailsWhenDefined() {
        JdbcConnectionDetails connectionDetails = mock(JdbcConnectionDetails.class);
        given(connectionDetails.getUsername()).willReturn(USERNAME);
        given(connectionDetails.getPassword()).willReturn(PASSWORD);
        given(connectionDetails.getJdbcUrl()).willReturn(JDBC_URL);
        given(connectionDetails.getDriverClassName()).willReturn(DatabaseDriver.POSTGRESQL.getDriverClassName());
        given(connectionDetails.getXaDataSourceClassName())
            .willReturn(DatabaseDriver.POSTGRESQL.getXaDataSourceClassName());
        new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(XADataSourceAutoConfiguration.class))
            .withUserConfiguration(FromProperties.class)
            .withBean(JdbcConnectionDetails.class, () -> connectionDetails)
            .run((context) -> {
                assertThat(context).hasSingleBean(JdbcConnectionDetails.class)
                    .doesNotHaveBean(PropertiesJdbcConnectionDetails.class);
                MockXADataSourceWrapper wrapper = context.getBean(MockXADataSourceWrapper.class);
                PGXADataSource dataSource = (PGXADataSource) wrapper.getXaDataSource();
                assertThat(dataSource).isNotNull();
                assertThat(dataSource.getUrl()).startsWith(JDBC_URL);
                assertThat(dataSource.getUser()).isEqualTo(USERNAME);
                assertThat(dataSource.getPassword()).isEqualTo(PASSWORD);
            });
    }
}
