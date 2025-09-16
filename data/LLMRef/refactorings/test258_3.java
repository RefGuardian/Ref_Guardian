public class test258 {

    private DataSource getMigrationDataSource(DataSource flywayDataSource, DataSource dataSource,
    				FlywayConnectionDetails connectionDetails) {
    			if (flywayDataSource != null) {
    				return flywayDataSource;
    			}
    			if (urlPresent(connectionDetails)) {
    				DataSourceBuilder<?> builder = createBuilderWithUrl(connectionDetails);
    				return builder.build();
    			}
    			if (userPresent(connectionDetails, dataSource)) {
    				DataSourceBuilder<?> builder = createBuilderWithDerivedDataSource(dataSource);
    				return builder.build();
    			}
    			Assert.state(dataSource != null, "Flyway migration DataSource missing");
    			return dataSource;
    		}
    
    private boolean urlPresent(FlywayConnectionDetails connectionDetails) {
        String url = connectionDetails.getJdbcUrl();
        return url != null;
    }
    
    private DataSourceBuilder<?> createBuilderWithUrl(FlywayConnectionDetails connectionDetails) {
        String url = connectionDetails.getJdbcUrl();
        DataSourceBuilder<?> builder = DataSourceBuilder.create().type(SimpleDriverDataSource.class);
        builder.url(url);
        applyConnectionDetails(connectionDetails, builder);
        return builder;
    }
    
    private boolean userPresent(FlywayConnectionDetails connectionDetails, DataSource dataSource) {
        String user = connectionDetails.getUsername();
        return user != null && dataSource != null;
    }
    
    private DataSourceBuilder<?> createBuilderWithDerivedDataSource(DataSource dataSource) {
        DataSourceBuilder<?> builder = DataSourceBuilder.derivedFrom(dataSource)
            .type(SimpleDriverDataSource.class);
        applyConnectionDetails(connectionDetails, builder);
        return builder;
    }
}
