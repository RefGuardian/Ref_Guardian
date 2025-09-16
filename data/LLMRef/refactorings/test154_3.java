public class test154 {

    @Test
    	void customConnectionFactoryIsApplied() {
    		this.contextRunner.withUserConfiguration(EmptyConfiguration.class)
    			.withPropertyValues("spring.jms.cache.enabled=false",
    					"spring.activemq.brokerUrl=vm://localhost?useJmx=false&broker.persistent=false",
    					"spring.activemq.user=foo", "spring.activemq.password=bar", "spring.activemq.closeTimeout=500",
    					"spring.activemq.nonBlockingRedelivery=true", "spring.activemq.sendTimeout=1000",
    					"spring.activemq.packages.trust-all=false", "spring.activemq.packages.trusted=com.example.acme")
    			.run((context) -> assertConnectionFactoryProperties(context));
    	}

    private void assertConnectionFactoryProperties(ConfigurableApplicationContext context) {
        assertThat(context).hasSingleBean(ConnectionFactory.class)
            .hasSingleBean(ActiveMQConnectionFactory.class)
            .hasBean("jmsConnectionFactory");
        ActiveMQConnectionFactory connectionFactory = context.getBean(ActiveMQConnectionFactory.class);
        assertThat(context.getBean("jmsConnectionFactory")).isSameAs(connectionFactory);
        assertThat(connectionFactory.getUserName()).isEqualTo("foo");
        assertThat(connectionFactory.getPassword()).isEqualTo("bar");
        assertThat(connectionFactory.getCloseTimeout()).isEqualTo(500);
        assertThat(connectionFactory.isNonBlockingRedelivery()).isTrue();
        assertThat(connectionFactory.getSendTimeout()).isEqualTo(1000);
        assertThat(connectionFactory.isTrustAllPackages()).isFalse();
        assertThat(connectionFactory.getTrustedPackages()).containsExactly("com.example.acme");
    }
}
