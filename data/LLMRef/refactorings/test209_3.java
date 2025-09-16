public class test209 {

    protected static ConnectionFactory createConnectionFactory(R2dbcProperties properties,
                                                              R2dbcConnectionDetails connectionDetails, ClassLoader classLoader,
                                                              List<ConnectionFactoryOptionsBuilderCustomizer> optionsCustomizers,
                                                              List<ConnectionFactoryDecorator> decorators) {
        try {
            ConnectionFactoryOptions options = new ConnectionFactoryOptionsInitializer().initialize(properties, connectionDetails,
                    () -> EmbeddedDatabaseConnection.get(classLoader));

            configureOptions(options, optionsCustomizers);

            return org.springframework.boot.r2dbc.ConnectionFactoryBuilder
                    .withOptions(options)
                    .decorators(decorators)
                    .build();
        } catch (IllegalStateException ex) {
            handleIllegalStateException(ex, classLoader);
        }
    }

    private static void configureOptions(ConnectionFactoryOptions options, List<ConnectionFactoryOptionsBuilderCustomizer> optionsCustomizers) {
        for (ConnectionFactoryOptionsBuilderCustomizer optionsCustomizer : optionsCustomizers) {
            optionsCustomizer.customize(options);
        }
    }

    private static void handleIllegalStateException(IllegalStateException ex, ClassLoader classLoader) {
        String message = ex.getMessage();
        if (message != null && message.contains("driver=pool")
                && !ClassUtils.isPresent("io.r2dbc.pool.ConnectionPool", classLoader)) {
            throw new MissingR2dbcPoolDependencyException();
        }
        throw ex;
    }
}
