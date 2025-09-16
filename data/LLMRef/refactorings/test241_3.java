public class test241 {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnMissingBean
    EmbeddedActiveMQ embeddedActiveMq(org.apache.activemq.artemis.core.config.Configuration configuration,
        JMSConfiguration jmsConfiguration,
        ObjectProvider<ArtemisConfigurationCustomizer> configurationCustomizers) {
        
        setupQueueConfigurations(configuration, jmsConfiguration);
        setupTopicConfigurations(configuration, jmsConfiguration);
        customizeConfigurations(configuration, configurationCustomizers);
        
        EmbeddedActiveMQ embeddedActiveMq = new EmbeddedActiveMQ();
        embeddedActiveMq.setConfiguration(configuration);
        return embeddedActiveMq;
    }

    private void setupQueueConfigurations(org.apache.activemq.artemis.core.config.Configuration configuration, JMSConfiguration jmsConfiguration) {
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

    private void setupTopicConfigurations(org.apache.activemq.artemis.core.config.Configuration configuration, JMSConfiguration jmsConfiguration) {
        for (TopicConfiguration topicConfiguration : jmsConfiguration.getTopicConfigurations()) {
            configuration.addAddressConfiguration(new CoreAddressConfiguration().setName(topicConfiguration.getName())
                .addRoutingType(RoutingType.MULTICAST));
        }
    }

    private void customizeConfigurations(org.apache.activemq.artemis.core.config.Configuration configuration, ObjectProvider<ArtemisConfigurationCustomizer> configurationCustomizers) {
        configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(configuration));
    }
}
