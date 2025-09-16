public class test149 {

    @Test
    void autoConfiguresBeans() {
        autoConfiguresBeansCheckBeans(context);
    }

    private void autoConfiguresBeansCheckBeans(org.springframework.context.ApplicationContext context) {
        assertThat(context).hasSingleBean(PulsarConfiguration.class)
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
                .hasSingleBean(PulsarReaderEndpointRegistry.class);
    }
}
