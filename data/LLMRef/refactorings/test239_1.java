public class test239 {

    private DataSource getMigrationDataSource(DataSource liquibaseDataSource, DataSource dataSource,
    				LiquibaseConnectionDetails connectionDetails) {
    			if (liquibaseDataSource != null) {
    				return liquibaseDataSource;
    			}
    			String url = connectionDetails.getJdbcUrl();
    			if (url != null) {
    				DataSourceBuilder<?> builder = createBuilder(url, connectionDetails);
    				return builder.build();
    			}
    			String user = connectionDetails.getUsername();
    			if (user != null && dataSource != null) {
    				DataSourceBuilder<?> builder = derivedBuilder(dataSource, connectionDetails);
    				return builder.build();
    			}
    			Assert.state(dataSource != null, "Liquibase migration DataSource missing");
    			return dataSource;
    		}
    
    private DataSourceBuilder<?> createBuilder(String url, LiquibaseConnectionDetails connectionDetails) {
        DataSourceBuilder<?> builder = DataSourceBuilder.create().type(SimpleDriverDataSource.class);
        builder.url(url);
        applyConnectionDetails(connectionDetails, builder);
        return builder;
    }

    private DataSourceBuilder<?> derivedBuilder(DataSource dataSource, LiquibaseConnectionDetails connectionDetails) {
        DataSourceBuilder<?> builder = DataSourceBuilder.derivedFrom(dataSource)
            .type(SimpleDriverDataSource.class);
        applyConnectionDetails(connectionDetails, builder);
        return builder;
    }

}
