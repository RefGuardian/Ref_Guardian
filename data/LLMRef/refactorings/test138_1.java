public class test138 {

    private static final String CONFIG_PROCESSOR_BEAN_NAME = AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME;

    @Test
    void initializeWhenUsingSupplierDecorates() {
        GenericApplicationContext context = new GenericApplicationContext();
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getBeanFactory();
        ConfigurationClassPostProcessor configurationAnnotationPostProcessor = mock(ConfigurationClassPostProcessor.class);
        BeanDefinition beanDefinition = BeanDefinitionBuilder
            .rootBeanDefinition(ConfigurationClassPostProcessor.class, () -> configurationAnnotationPostProcessor)
            .getBeanDefinition();
        registry.registerBeanDefinition(CONFIG_PROCESSOR_BEAN_NAME, beanDefinition);
        CachingMetadataReaderFactoryPostProcessor postProcessor = new CachingMetadataReaderFactoryPostProcessor(context);
        postProcessor.postProcessBeanDefinitionRegistry(registry);
        context.refresh();
        ConfigurationClassPostProcessor bean = context.getBean(ConfigurationClassPostProcessor.class);
        assertThat(bean).isSameAs(configurationAnnotationPostProcessor);
        then(configurationAnnotationPostProcessor).should()
            .setMetadataReaderFactory(assertArg((metadataReaderFactory) ->
                assertThat(metadataReaderFactory).isInstanceOf(ConcurrentReferenceCachingMetadataReaderFactory.class)));
    }
}
