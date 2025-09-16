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
        
        handleResourceType(registry, WEBJARS_PATH_PATTERN, "classpath:/META-INF/resources/webjars/", resourceHandlerRegistrationCustomizers);
        handleResourceType(registry, STATIC_PATH_PATTERN, this.resourceProperties.getStaticLocations(), resourceHandlerRegistrationCustomizers);
    }

    private void handleResourceType(ResourceHandlerRegistry registry, String pathPattern, String resourceLocations, List<ResourceHandlerRegistrationCustomizer> customizers) {
        if (!registry.hasMappingForPattern(pathPattern)) {
            ResourceHandlerRegistration registration = registry.addResourceHandler(pathPattern)
                .addResourceLocations(resourceLocations);
            configureResourceCaching(registration);
            customizers.forEach((customizer) -> customizer.customize(registration));
        }
    }

}
