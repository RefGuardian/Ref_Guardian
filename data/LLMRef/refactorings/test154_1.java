public class test154 {

    private static final String BROKER_URL = "vm://localhost?useJmx=false&broker.persistent=false";
    private static final String USER_NAME = "foo";
    private static final String PASSWORD = "bar";
    private static final int CLOSE_TIMEOUT = 500;
    private static final boolean NON_BLOCKING_REDELIVERY = true;
    private static final int SEND_TIMEOUT = 1000;
    private static final boolean TRUST_ALL_PACKAGES = false;
    private static final List<String> TRUSTED_PACKAGES = Collections.singletonList("com.example.acme");

    @Test
	void customConnectionFactoryIsApplied() {
		this.contextRunner.withUserConfiguration(EmptyConfiguration.class)
			.withPropertyValues("spring.jms.cache.enabled=false", "spring.activemq.brokerUrl=" + BROKER_URL,
				"spring.activemq.user=" + USER_NAME, "spring.activemq.password=" + PASSWORD,
				"spring.activemq.closeTimeout=" + CLOSE_TIMEOUT, "spring.activemq.nonBlockingRedelivery=" + NON_BLOCKING_REDELIVERY,
				"spring.activemq.sendTimeout=" + SEND_TIMEOUT, "spring.activemq.packages.trust-all=" + TRUST_ALL_PACKAGES,
				"spring.activemq.packages.trusted=" + TRUSTED_PACKAGES.get(0))
			.run((context) -> {
				assertThat(context).hasSingleBean(ConnectionFactory.class)
					.hasSingleBean(ActiveMQConnectionFactory.class)
					.hasBean("jmsConnectionFactory");
				ActiveMQConnectionFactory connectionFactory = context.getBean(ActiveMQConnectionFactory.class);
				assertThat(context.getBean("jmsConnectionFactory")).isSameAs(connectionFactory);
				assertThat(connectionFactory.getUserName()).isEqualTo(USER_NAME);
				assertThat(connectionFactory.getPassword()).isEqualTo(PASSWORD);
				assertThat(connectionFactory.getCloseTimeout()).isEqualTo(CLOSE_TIMEOUT);
				assertThat(connectionFactory.isNonBlockingRedelivery()).isTrue();
				assertThat(connectionFactory.getSendTimeout()).isEqualTo(SEND_TIMEOUT);
				assertThat(connectionFactory.isTrustAllPackages()).isFalse();
				assertThat(connectionFactory.getTrustedPackages()).containsExactlyElementsOf(TRUSTED_PACKAGES);
			});
	}
}
