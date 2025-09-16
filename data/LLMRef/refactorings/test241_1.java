public class test241 {

    @Bean(initMethod = "start", destroyMethod = "stop")
    	@ConditionalOnMissingBean
    	EmbeddedActiveMQ embeddedActiveMq(org.apache.activemq.artemis.core.config.Configuration configuration,
    			JMSConfiguration jmsConfiguration,
    			ObjectProvider<ArtemisConfigurationCustomizer> configurationCustomizers) {
    		createQueueConfigurations(configuration, jmsConfiguration);
    		createTopicConfigurations(configuration, jmsConfiguration);
    		configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(configuration));
    		return createEmbeddedActiveMq(configuration);
    }

    private void createQueueConfigurations(org.apache.activemq.artemis.core.config.Configuration configuration, JMSConfiguration jmsConfiguration) {
		for (JMSQueueConfiguration queueConfiguration : jmsConfiguration.getQueueConfigurations()) {
			String queueName = queueConfiguration.getName();
			configuration.addAddressConfiguration(new CoreAddressConfiguration().setName(queueName)
				.addRoutingType(RoutingType.ANYCAST)
				.addQueueConfiguration(QueueConfiguration.of(queueName)
					.setAddress(queueName)
					.setFilterString(queueConfiguration.getSelector())
					.setDurable(queueConfiguration.isDurable())
					.setRoutingType(RoutingType.ANYCAST)));
		}
    }

    private void createTopicConfigurations(org.apache.activemq.artemis.core.config.Configuration configuration, JMSConfiguration jmsConfiguration) {
		for (TopicConfiguration topicConfiguration : jmsConfiguration.getTopicConfigurations()) {
			configuration.addAddressConfiguration(new CoreAddressConfiguration().setName(topicConfiguration.getName())
				.addRoutingType(RoutingType.MULTICAST));
		}
    }

    private EmbeddedActiveMQ createEmbeddedActiveMq(org.apache.activemq.artemis.core.config.Configuration configuration) {
		EmbeddedActiveMQ embeddedActiveMq = new EmbeddedActiveMQ();
		embeddedActiveMq.setConfiguration(configuration);
		return embeddedActiveMq;
    }
}
