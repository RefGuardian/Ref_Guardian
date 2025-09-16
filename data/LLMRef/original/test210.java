public class test210 {

    @Bean(destroyMethod = "dispose")
    			ConnectionPool connectionFactory(R2dbcProperties properties,
    					ObjectProvider<R2dbcConnectionDetails> connectionDetails, ResourceLoader resourceLoader,
    					ObjectProvider<ConnectionFactoryOptionsBuilderCustomizer> customizers,
    					ObjectProvider<ConnectionFactoryDecorator> decorators) {
    				ConnectionFactory connectionFactory = createConnectionFactory(properties,
    						connectionDetails.getIfAvailable(), resourceLoader.getClassLoader(),
    						customizers.orderedStream().toList(), decorators.orderedStream().toList());
    				R2dbcProperties.Pool pool = properties.getPool();
    				PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
    				ConnectionPoolConfiguration.Builder builder = ConnectionPoolConfiguration.builder(connectionFactory);
    				map.from(pool.getMaxIdleTime()).to(builder::maxIdleTime);
    				map.from(pool.getMaxLifeTime()).to(builder::maxLifeTime);
    				map.from(pool.getMaxAcquireTime()).to(builder::maxAcquireTime);
    				map.from(pool.getAcquireRetry()).to(builder::acquireRetry);
    				map.from(pool.getMaxCreateConnectionTime()).to(builder::maxCreateConnectionTime);
    				map.from(pool.getInitialSize()).to(builder::initialSize);
    				map.from(pool.getMaxSize()).to(builder::maxSize);
    				map.from(pool.getValidationQuery()).whenHasText().to(builder::validationQuery);
    				map.from(pool.getValidationDepth()).to(builder::validationDepth);
    				map.from(pool.getMinIdle()).to(builder::minIdle);
    				map.from(pool.getMaxValidationTime()).to(builder::maxValidationTime);
    				return new ConnectionPool(builder.build());
    			}
}
