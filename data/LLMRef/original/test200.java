public class test200 {

    @Override
    		public void addResourceHandlers(ResourceHandlerRegistry registry) {
    			if (!this.resourceProperties.isAddMappings()) {
    				logger.debug("Default resource handling disabled");
    				return;
    			}
    			List<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizers = this.resourceHandlerRegistrationCustomizers
    				.orderedStream()
    				.toList();
    			String webjarsPathPattern = this.webFluxProperties.getWebjarsPathPattern();
    			if (!registry.hasMappingForPattern(webjarsPathPattern)) {
    				ResourceHandlerRegistration registration = registry.addResourceHandler(webjarsPathPattern)
    					.addResourceLocations("classpath:/META-INF/resources/webjars/");
    				configureResourceCaching(registration);
    				resourceHandlerRegistrationCustomizers.forEach((customizer) -> customizer.customize(registration));
    			}
    			String staticPathPattern = this.webFluxProperties.getStaticPathPattern();
    			if (!registry.hasMappingForPattern(staticPathPattern)) {
    				ResourceHandlerRegistration registration = registry.addResourceHandler(staticPathPattern)
    					.addResourceLocations(this.resourceProperties.getStaticLocations());
    				configureResourceCaching(registration);
    				resourceHandlerRegistrationCustomizers.forEach((customizer) -> customizer.customize(registration));
    			}
    		}
}
