public class test241 {

    @Bean(initMethod = "start", destroyMethod = "stop")
    	@ConditionalOnMissingBean
    	EmbeddedActiveMQ embeddedActiveMq(org.apache.activemq.artemis.core.config.Configuration configuration,
    			JMSConfiguration jmsConfiguration,
    			ObjectProvider<ArtemisConfigurationCustomizer> configurationCustomizers) {
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
    		for (TopicConfiguration topicConfiguration : jmsConfiguration.getTopicConfigurations()) {
    			configuration.addAddressConfiguration(new CoreAddressConfiguration().setName(topicConfiguration.getName())
    				.addRoutingType(RoutingType.MULTICAST));
    		}
    		configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(configuration));
    		EmbeddedActiveMQ embeddedActiveMq = new EmbeddedActiveMQ();
    		embeddedActiveMq.setConfiguration(configuration);
    		return embeddedActiveMq;
    	}
}
