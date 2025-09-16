public class test158 {

    @Test
    void testDefaultJmsListenerConfiguration() {
        this.contextRunner.withUserConfiguration(TestConfiguration.class).run((loaded) -> {
            ConnectionFactory connectionFactory = loaded.getBean(ConnectionFactory.class);
            assertThat(loaded).hasSingleBean(DefaultJmsListenerContainerFactory.class);
            DefaultJmsListenerContainerFactory containerFactory = loaded
                    .getBean(DefaultJmsListenerContainerFactory.class);
            SimpleJmsListenerEndpoint jmsListenerEndpoint = new SimpleJmsListenerEndpoint();
            jmsListenerEndpoint.setMessageListener((message) -> {
            });
            DefaultMessageListenerContainer container = containerFactory.createListenerContainer(jmsListenerEndpoint);
            assertThat(container.getClientId()).isNull();
            assertThat(container.getConcurrentConsumers()).isEqualTo(1);
            assertThat(container.getConnectionFactory()).isSameAs(connectionFactory);
            assertThat(container.getMaxConcurrentConsumers()).isEqualTo(1);
            assertThat(container.getSessionAcknowledgeMode()).isEqualTo(Session.AUTO_ACKNOWLEDGE);
            assertThat(container.isAutoStartup()).isTrue();
            assertThat(container.isPubSubDomain()).isFalse();
            assertThat(container.isSubscriptionDurable()).isFalse();
            assertThat(container).hasFieldOrPropertyWithValue("receiveTimeout", 1000L);
        });
    }
}
