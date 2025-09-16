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
                assertConcurrentConsumers(container);
                assertConnectionFactory(container, connectionFactory);
                assertMaxConcurrentConsumers(container);
                assertSessionAcknowledgeMode(container);
                assertAutoStartup(container);
                assertPubSubDomain(container);
                assertSubscriptionDurable(container);
                assertReceiveTimeout(container);
    		});
    	}

    private void assertReceiveTimeout(DefaultMessageListenerContainer container) {
        assertThat(container).hasFieldOrPropertyWithValue("receiveTimeout", 1000L);
    }

    private void assertSubscriptionDurable(DefaultMessageListenerContainer container) {
        assertThat(container.isSubscriptionDurable()).isFalse();
    }

    private void assertPubSubDomain(DefaultMessageListenerContainer container) {
        assertThat(container.isPubSubDomain()).isFalse();
    }

    private void assertAutoStartup(DefaultMessageListenerContainer container) {
        assertThat(container.isAutoStartup()).isTrue();
    }

    private void assertSessionAcknowledgeMode(DefaultMessageListenerContainer container) {
        assertThat(container.getSessionAcknowledgeMode()).isEqualTo(Session.AUTO_ACKNOWLEDGE);
    }

    private void assertMaxConcurrentConsumers(DefaultMessageListenerContainer container) {
        assertThat(container.getMaxConcurrentConsumers()).isEqualTo(1);
    }

    private void assertConnectionFactory(DefaultMessageListenerContainer container, ConnectionFactory connectionFactory) {
        assertThat(container.getConnectionFactory()).isSameAs(connectionFactory);
    }

    private void assertConcurrentConsumers(DefaultMessageListenerContainer container) {
        assertThat(container.getConcurrentConsumers()).isEqualTo(1);
    }
}
