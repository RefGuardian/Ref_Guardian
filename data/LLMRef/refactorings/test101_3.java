public class test101 {

    @Test
    void testDefaultRabbitConfiguration() {
        this.contextRunner.withUserConfiguration(TestConfiguration.class).run((context) -> {
            RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
            RabbitMessagingTemplate messagingTemplate = context.getBean(RabbitMessagingTemplate.class);
            CachingConnectionFactory connectionFactory = context.getBean(CachingConnectionFactory.class);
            RabbitAdmin amqpAdmin = context.getBean(RabbitAdmin.class);

            assertRabbitPropertyValues(rabbitTemplate, connectionFactory, messagingTemplate, amqpAdmin, context);
        });
    }

    private void assertRabbitPropertyValues(RabbitTemplate rabbitTemplate, CachingConnectionFactory connectionFactory,
        RabbitMessagingTemplate messagingTemplate, RabbitAdmin amqpAdmin, ApplicationContext context) {
        assertThat(rabbitTemplate.getConnectionFactory()).isEqualTo(connectionFactory);
        assertThat(getMandatory(rabbitTemplate)).isFalse();
        assertThat(messagingTemplate.getRabbitTemplate()).isEqualTo(rabbitTemplate);
        assertThat(amqpAdmin).isNotNull();
        assertThat(connectionFactory.getHost()).isEqualTo("localhost");
        assertThat(getTargetConnectionFactory(context).getRequestedChannelMax())
            .isEqualTo(com.rabbitmq.client.ConnectionFactory.DEFAULT_CHANNEL_MAX);
        assertThat(connectionFactory.isPublisherConfirms()).isFalse();
        assertThat(connectionFactory.isPublisherReturns()).isFalse();
        assertThat(connectionFactory.getRabbitConnectionFactory().getChannelRpcTimeout())
            .isEqualTo(com.rabbitmq.client.ConnectionFactory.DEFAULT_CHANNEL_RPC_TIMEOUT);
        assertThat(context.containsBean("rabbitListenerContainerFactory"))
            .as("Listener container factory should be created by default")
            .isTrue();
    }
}
