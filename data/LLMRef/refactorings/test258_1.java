public class test258 {

    private DataSource getMigrationDataSource(DataSource flywayDataSource, DataSource dataSource,
                    FlywayConnectionDetails connectionDetails) {
                if (flywayDataSource != null) {
                    return flywayDataSource;
                }
                String url = connectionDetails.getJdbcUrl();
                if (url != null) {
                    DataSourceBuilder<?> builder = DataSourceBuilder.create().type(SimpleDriverDataSource.class);
                    builder.url(url);
                    applyConnectionDetails(connectionDetails, builder);
                    return builder.build();
                }
                String user = connectionDetails.getUsername();
                if (user != null && dataSource != null) {
                    DataSourceBuilder<?> builder = DataSourceBuilder.derivedFrom(dataSource)
                        .type(SimpleDriverDataSource.class);
                    applyConnectionDetails(connectionDetails, builder);
                    return builder.build();
                }
                Assert.state(dataSource != null, "Flyway migration DataSource missing");
                return dataSource;
            }
}
