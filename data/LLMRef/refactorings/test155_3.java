public class test155 {

    @Test
    void defaultPoolConnectionFactoryIsApplied() {
        this.contextRunner.withUserConfiguration(EmptyConfiguration.class)
            .withPropertyValues("spring.activemq.pool.enabled=true")
            .run((context) -> {
                assertPoolConnectionFactory(context);
            });
    }

    private void assertPoolConnectionFactory(ApplicationContext context) {
        assertThat(context).hasSingleBean(ConnectionFactory.class)
            .hasSingleBean(JmsPoolConnectionFactory.class)
            .hasBean("jmsConnectionFactory");
        JmsPoolConnectionFactory connectionFactory = context.getBean(JmsPoolConnectionFactory.class);
        assertThat(context.getBean("jmsConnectionFactory")).isSameAs(connectionFactory);
        JmsPoolConnectionFactory defaultFactory = new JmsPoolConnectionFactory();
        assertThat(connectionFactory.isBlockIfSessionPoolIsFull())
            .isEqualTo(defaultFactory.isBlockIfSessionPoolIsFull());
        assertThat(connectionFactory.getBlockIfSessionPoolIsFullTimeout())
            .isEqualTo(defaultFactory.getBlockIfSessionPoolIsFullTimeout());
        assertThat(connectionFactory.getConnectionIdleTimeout())
            .isEqualTo(defaultFactory.getConnectionIdleTimeout());
        assertThat(connectionFactory.getMaxConnections()).isEqualTo(defaultFactory.getMaxConnections());
        assertThat(connectionFactory.getMaxSessionsPerConnection())
            .isEqualTo(defaultFactory.getMaxSessionsPerConnection());
        assertThat(connectionFactory.getConnectionCheckInterval())
            .isEqualTo(defaultFactory.getConnectionCheckInterval());
        assertThat(connectionFactory.isUseAnonymousProducers())
            .isEqualTo(defaultFactory.isUseAnonymousProducers());
    }
}
