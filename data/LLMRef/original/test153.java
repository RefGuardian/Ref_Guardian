public class test153 {

    @Test
    	void connectionFactoryCachingCanBeDisabled() {
    		this.contextRunner.withUserConfiguration(EmptyConfiguration.class)
    			.withPropertyValues("spring.jms.cache.enabled=false")
    			.run((context) -> {
    				assertThat(context).hasSingleBean(ConnectionFactory.class)
    					.hasSingleBean(ActiveMQConnectionFactory.class)
    					.hasBean("jmsConnectionFactory");
    				ActiveMQConnectionFactory connectionFactory = context.getBean(ActiveMQConnectionFactory.class);
    				assertThat(context.getBean("jmsConnectionFactory")).isSameAs(connectionFactory);
    				ActiveMQConnectionFactory defaultFactory = new ActiveMQConnectionFactory(
    						"vm://localhost?broker.persistent=false");
    				assertThat(connectionFactory.getUserName()).isEqualTo(defaultFactory.getUserName());
    				assertThat(connectionFactory.getPassword()).isEqualTo(defaultFactory.getPassword());
    				assertThat(connectionFactory.getCloseTimeout()).isEqualTo(defaultFactory.getCloseTimeout());
    				assertThat(connectionFactory.isNonBlockingRedelivery())
    					.isEqualTo(defaultFactory.isNonBlockingRedelivery());
    				assertThat(connectionFactory.getSendTimeout()).isEqualTo(defaultFactory.getSendTimeout());
    				assertThat(connectionFactory.isTrustAllPackages()).isEqualTo(defaultFactory.isTrustAllPackages());
    				assertThat(connectionFactory.getTrustedPackages())
    					.containsExactly(StringUtils.toStringArray(defaultFactory.getTrustedPackages()));
    			});
    	}
}
