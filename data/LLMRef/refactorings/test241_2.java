public class test241 {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnMissingBean
    EmbeddedActiveMQ embeddedActiveMq(org.apache.activemq.artemis.core.config.Configuration configuration,
                                     JMSConfiguration jmsConfiguration,
                                     ObjectProvider<ArtemisConfigurationCustomizer> configurationCustomizers) {
        configureQueueConfigurations(configuration, jmsConfiguration);
        configureTopicConfigurations(configuration, jmsConfiguration);
        customizeConfiguration(configuration, configurationCustomizers);
        EmbeddedActiveMQ embeddedActiveMq = new EmbeddedActiveMQ();
        embeddedActiveMq.setConfiguration(configuration);
        return embeddedActiveMq;
    }

    private void configureQueueConfigurations(org.apache.activemq.artemis.core.config.Configuration configuration,
                                              JMSConfiguration jmsConfiguration) {
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

    private void configureTopicConfigurations(org.apache.activemq.artemis.core.config.Configuration configuration,
                                              JMSConfiguration jmsConfiguration) {
        for (TopicConfiguration topicConfiguration : jmsConfiguration.getTopicConfigurations()) {
            configuration.addAddressConfiguration(new CoreAddressConfiguration().setName(topicConfiguration.getName())
                    .addRoutingType(RoutingType.MULTICAST));
        }
    }

    private void customizeConfiguration(org.apache.activemq.artemis.core.config.Configuration configuration,
                                        ObjectProvider<ArtemisConfigurationCustomizer> configurationCustomizers) {
        configurationCustomizers.orderedStream().forEach((customizer) -> customizer.customize(configuration));
    }
}
