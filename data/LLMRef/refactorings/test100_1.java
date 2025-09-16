public class test100 {

    @Test
    	void configureWithUrlAndPoolPropertiesApplyProperties() {
    		this.contextRunner
    			.withPropertyValues("spring.r2dbc.url:r2dbc:h2:mem:///" + randomDatabaseName(),
    					"spring.r2dbc.pool.max-size=15", "spring.r2dbc.pool.max-acquire-time=3m",
    					"spring.r2dbc.pool.acquire-retry=5", "spring.r2dbc.pool.min-idle=1",
    					"spring.r2dbc.pool.max-validation-time=1s", "spring.r2dbc.pool.initial-size=0")
    			.run((context) -> {
    				assertThat(context).hasSingleBean(ConnectionFactory.class)
    					.hasSingleBean(ConnectionPool.class)
    					.hasSingleBean(R2dbcProperties.class);
    				ConnectionPool connectionPool = context.getBean(ConnectionPool.class);
    				connectionPool.warmup().block();
    				try {
    					PoolMetrics poolMetrics = connectionPool.getMetrics().get();
    					assertThat(poolMetrics.idleSize()).isEqualTo(1);
    					assertThat(poolMetrics.getMaxAllocatedSize()).isEqualTo(15);
    					assertThat(connectionPool).hasFieldOrPropertyWithValue("maxAcquireTime", Duration.ofMinutes(3));
    					assertThat(connectionPool).hasFieldOrPropertyWithValue("maxValidationTime", Duration.ofSeconds(1));
    					assertThat(connectionPool).extracting("create").satisfies((mono) -> {
    						assertThat(mono.getClass().getName()).endsWith("MonoRetry");
    						assertThat(mono).hasFieldOrPropertyWithValue("times", 5L);
    					});
    				}
    				finally {
    					connectionPool.close().block();
    				}
    			});
    	}

	private String randomDatabaseName() {
		// implementation of randomDatabaseName
		return "";
	}
}
