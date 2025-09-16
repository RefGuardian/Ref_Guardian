public class test156 {

    @Test
    	void customPoolConnectionFactoryIsApplied() {
    		this.contextRunner.withUserConfiguration(EmptyConfiguration.class)
    			.withPropertyValues("spring.activemq.pool.enabled=true", "spring.activemq.pool.blockIfFull=false",
    					"spring.activemq.pool.blockIfFullTimeout=64", "spring.activemq.pool.idleTimeout=512",
    					"spring.activemq.pool.maxConnections=256", "spring.activemq.pool.maxSessionsPerConnection=1024",
    					"spring.activemq.pool.timeBetweenExpirationCheck=2048",
    					"spring.activemq.pool.useAnonymousProducers=false")
    			.run((context) -> {
    				assertThat(context).hasSingleBean(ConnectionFactory.class)
    					.hasSingleBean(JmsPoolConnectionFactory.class)
    					.hasBean("jmsConnectionFactory");
    				JmsPoolConnectionFactory connectionFactory = context.getBean(JmsPoolConnectionFactory.class);
    				assertThat(context.getBean("jmsConnectionFactory")).isSameAs(connectionFactory);
    				assertThat(connectionFactory.isBlockIfSessionPoolIsFull()).isFalse();
    				assertThat(connectionFactory.getBlockIfSessionPoolIsFullTimeout()).isEqualTo(64);
    				assertThat(connectionFactory.getConnectionIdleTimeout()).isEqualTo(512);
    				assertThat(connectionFactory.getMaxConnections()).isEqualTo(256);
    				assertThat(connectionFactory.getMaxSessionsPerConnection()).isEqualTo(1024);
    				assertThat(connectionFactory.getConnectionCheckInterval()).isEqualTo(2048);
    				assertThat(connectionFactory.isUseAnonymousProducers()).isFalse();
    			});
    	}
}
