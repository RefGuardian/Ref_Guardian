public class test112 {

    private static final String DRIVER_CLASS_NAME = DatabaseDriver.POSTGRESQL.getDriverClassName();
    private static final String XA_DATA_SOURCE_CLASS_NAME = DatabaseDriver.POSTGRESQL.getXaDataSourceClassName();

    @Test
    void shouldUseCustomConnectionDetailsWhenDefined() {
        JdbcConnectionDetails connectionDetails = mock(JdbcConnectionDetails.class);
        given(connectionDetails.getUsername()).willReturn("user-1");
        given(connectionDetails.getPassword()).willReturn("password-1");
        given(connectionDetails.getJdbcUrl()).willReturn("jdbc:postgresql://postgres.example.com:12345/database-1");
        given(connectionDetails.getDriverClassName()).willReturn(DRIVER_CLASS_NAME);
        given(connectionDetails.getXaDataSourceClassName())
                .willReturn(XA_DATA_SOURCE_CLASS_NAME);
        new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(XADataSourceAutoConfiguration.class))
                .withUserConfiguration(FromProperties.class)
                .withBean(JdbcConnectionDetails.class, () -> connectionDetails)
                .run((context) -> {
                    assertThat(context).hasSingleBean(JdbcConnectionDetails.class)
                            .doesNotHaveBean(PropertiesJdbcConnectionDetails.class);
                    MockXADataSourceWrapper wrapper = context.getBean(MockXADataSourceWrapper.class);
                    PGXADataSource dataSource = (PGXADataSource) wrapper.getXaDataSource();
                    assertThat(dataSource).isNotNull();
                    assertThat(dataSource.getUrl()).startsWith("jdbc:postgresql://postgres.example.com:12345/database-1");
                    assertThat(dataSource.getUser()).isEqualTo("user-1");
                    assertThat(dataSource.getPassword()).isEqualTo("password-1");
                });
    }
}
