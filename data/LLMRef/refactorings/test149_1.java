public class test149 {

    private static final String BEAN_NAME = "PulsarConfiguration.class";

    @Test
    void autoConfiguresBeans() {
        this.contextRunner.run((context) -> assertThat(context).hasSingleBean(BEAN_NAME)
                .hasSingleBean(PulsarConnectionDetails.class)
                .hasSingleBean(DefaultPulsarClientFactory.class)
                .hasSingleBean(PulsarClient.class)
                .hasSingleBean(PulsarTopicBuilder.class)
                .hasSingleBean(PulsarAdministration.class)
                .hasSingleBean(DefaultSchemaResolver.class)
                .hasSingleBean(DefaultTopicResolver.class)
                .hasSingleBean(CachingPulsarProducerFactory.class)
                .hasSingleBean(PulsarTemplate.class)
                .hasSingleBean(DefaultPulsarConsumerFactory.class)
                .hasSingleBean(ConcurrentPulsarListenerContainerFactory.class)
                .hasSingleBean(DefaultPulsarReaderFactory.class)
                .hasSingleBean(DefaultPulsarReaderContainerFactory.class)
                .hasSingleBean(PulsarListenerAnnotationBeanPostProcessor.class)
                .hasSingleBean(PulsarListenerEndpointRegistry.class)
                .hasSingleBean(PulsarReaderAnnotationBeanPostProcessor.class)
                .hasSingleBean(PulsarReaderEndpointRegistry.class));
    }
}
