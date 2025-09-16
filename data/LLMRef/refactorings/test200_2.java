public class test200 {

    private static final String WEBJARS_PATH_PATTERN = this.webFluxProperties.getWebjarsPathPattern();
    private static final String STATIC_PATH_PATTERN = this.webFluxProperties.getStaticPathPattern();
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!this.resourceProperties.isAddMappings()) {
            logger.debug("Default resource handling disabled");
            return;
        }
        List<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizers = this.resourceHandlerRegistrationCustomizers
            .orderedStream()
            .toList();
        
        if (!registry.hasMappingForPattern(WEBJARS_PATH_PATTERN)) {
            ResourceHandlerRegistration registration = registry.addResourceHandler(WEBJARS_PATH_PATTERN)
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
            configureResourceCaching(registration);
            resourceHandlerRegistrationCustomizers.forEach((customizer) -> customizer.customize(registration));
        }
        
        if (!registry.hasMappingForPattern(STATIC_PATH_PATTERN)) {
            ResourceHandlerRegistration registration = registry.addResourceHandler(STATIC_PATH_PATTERN)
                .addResourceLocations(this.resourceProperties.getStaticLocations());
            configureResourceCaching(registration);
            resourceHandlerRegistrationCustomizers.forEach((customizer) -> customizer.customize(registration));
        }
    }
}
