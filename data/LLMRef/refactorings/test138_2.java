public class test138 {

    @Test
    void initializeWhenUsingSupplierDecorates() {
        GenericApplicationContext context = new GenericApplicationContext();
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
        ConfigurationClassPostProcessor configurationAnnotationPostProcessor = mock(
                ConfigurationClassPostProcessor.class);
        registry.registerBeanDefinition(AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME,
                BeanDefinitionBuilder
                        .rootBeanDefinition(ConfigurationClassPostProcessor.class, () -> configurationAnnotationPostProcessor)
                        .getBeanDefinition());
        CachingMetadataReaderFactoryPostProcessor postProcessor = new CachingMetadataReaderFactoryPostProcessor(
                context);
        postProcessor.postProcessBeanDefinitionRegistry(registry);
        context.refresh();
        ConfigurationClassPostProcessor bean = context.getBean(ConfigurationClassPostProcessor.class);
        assertThat(bean).isSameAs(configurationAnnotationPostProcessor);
        then(configurationAnnotationPostProcessor).should()
                .setMetadataReaderFactory(assertArg((metadataReaderFactory) -> assertThat(metadataReaderFactory)
                        .isInstanceOf(ConcurrentReferenceCachingMetadataReaderFactory.class)));
    }
}
