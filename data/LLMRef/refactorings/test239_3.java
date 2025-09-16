public class test239 {

    private DataSource getMigrationDataSource(DataSource liquibaseDataSource, DataSource dataSource,
    				LiquibaseConnectionDetails connectionDetails) {
    			if (liquibaseDataSource != null) {
    				return liquibaseDataSource;
    			}
    			String jdbcUrl = getJdbcUrl(connectionDetails);
    			if (jdbcUrl != null) {
    				DataSourceBuilder<?> builder = createDataSourceBuilder(SimpleDriverDataSource.class, jdbcUrl);
    				return buildDataSource(builder, connectionDetails);
    			}
    			String user = connectionDetails.getUsername();
    			if (user != null && dataSource != null) {
    				DataSourceBuilder<?> builder = createDerivedDataSourceBuilder(SimpleDriverDataSource.class, dataSource);
    				return buildDataSource(builder, connectionDetails);
    			}
    			Assert.state(dataSource != null, "Liquibase migration DataSource missing");
    			return dataSource;
    }
    
    private String getJdbcUrl(LiquibaseConnectionDetails connectionDetails) {
    		return connectionDetails.getJdbcUrl();
    }
    
    private DataSourceBuilder<?> createDataSourceBuilder(Class<? extends javax.sql.DataSource> type, String url) {
    		DataSourceBuilder<?> builder = DataSourceBuilder.create().type(type);
    		builder.url(url);
    		return builder;
    }
    
    private DataSourceBuilder<?> createDerivedDataSourceBuilder(Class<? extends javax.sql.DataSource> type, DataSource dataSource) {
    		DataSourceBuilder<?> builder = DataSourceBuilder.derivedFrom(dataSource).type(type);
    		return builder;
    }
    
    private DataSource buildDataSource(DataSourceBuilder<?> builder, LiquibaseConnectionDetails connectionDetails) {
    		applyConnectionDetails(connectionDetails, builder);
    		return builder.build();
    }
}
