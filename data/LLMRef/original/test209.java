public class test209 {

    protected static ConnectionFactory createConnectionFactory(R2dbcProperties properties,
    			R2dbcConnectionDetails connectionDetails, ClassLoader classLoader,
    			List<ConnectionFactoryOptionsBuilderCustomizer> optionsCustomizers,
    			List<ConnectionFactoryDecorator> decorators) {
    		try {
    			return org.springframework.boot.r2dbc.ConnectionFactoryBuilder
    				.withOptions(new ConnectionFactoryOptionsInitializer().initialize(properties, connectionDetails,
    						() -> EmbeddedDatabaseConnection.get(classLoader)))
    				.configure((options) -> {
    					for (ConnectionFactoryOptionsBuilderCustomizer optionsCustomizer : optionsCustomizers) {
    						optionsCustomizer.customize(options);
    					}
    				})
    				.decorators(decorators)
    				.build();
    		}
    		catch (IllegalStateException ex) {
    			String message = ex.getMessage();
    			if (message != null && message.contains("driver=pool")
    					&& !ClassUtils.isPresent("io.r2dbc.pool.ConnectionPool", classLoader)) {
    				throw new MissingR2dbcPoolDependencyException();
    			}
    			throw ex;
    		}
    	}
}
