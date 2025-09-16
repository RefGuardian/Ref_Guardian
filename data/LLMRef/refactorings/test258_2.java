public class test258 {

    private DataSource getMigrationDataSource(DataSource flywayDataSource, DataSource dataSource,
                                             FlywayConnectionDetails connectionDetails) {
        if (flywayDataSource != null) {
            return flywayDataSource;
        }
        String url = connectionDetails.getJdbcUrl();
        if (isUrlNotBlank(url)) {
            DataSourceBuilder<?> builder = createDataSourceBuilder(url, connectionDetails);
            return builder.build();
        }
        String user = connectionDetails.getUsername();
        if (isUserNotBlank(user) && dataSource != null) {
            DataSourceBuilder<?> builder = createDerivedDataSourceBuilder(dataSource, connectionDetails);
            return builder.build();
        }
        Assert.state(dataSource != null, "Flyway migration DataSource missing");
        return dataSource;
    }

    private boolean isUrlNotBlank(String url) {
        return url != null;
    }

    private DataSourceBuilder<?> createDataSourceBuilder(String url, FlywayConnectionDetails connectionDetails) {
        DataSourceBuilder<?> builder = DataSourceBuilder.create().type(SimpleDriverDataSource.class);
        builder.url(url);
        applyConnectionDetails(connectionDetails, builder);
        return builder;
    }

    private boolean isUserNotBlank(String user) {
        return user != null;
    }

    private DataSourceBuilder<?> createDerivedDataSourceBuilder(DataSource dataSource,
                                                               FlywayConnectionDetails connectionDetails) {
        DataSourceBuilder<?> builder = DataSourceBuilder.derivedFrom(dataSource).type(SimpleDriverDataSource.class);
        applyConnectionDetails(connectionDetails, builder);
        return builder;
    }
}
