public class test14 {

    private void configure(MergedContextConfiguration mergedConfig, SpringApplication application) {
    		application.setMainApplicationClass(mergedConfig.getTestClass());
    		application.addPrimarySources(Arrays.asList(mergedConfig.getClasses()));
    		application.getSources().addAll(Arrays.asList(mergedConfig.getLocations()));
    		List<ApplicationContextInitializer<?>> initializers = getInitializers(mergedConfig, application);
    		if (mergedConfig instanceof WebMergedContextConfiguration) {
    			application.setWebApplicationType(WebApplicationType.SERVLET);
    			if (!isEmbeddedWebEnvironment(mergedConfig)) {
    				new WebConfigurer().configure(mergedConfig, initializers);
    			}
    		}
    		else if (mergedConfig instanceof ReactiveWebMergedContextConfiguration) {
    			application.setWebApplicationType(WebApplicationType.REACTIVE);
    		}
    		else {
    			application.setWebApplicationType(WebApplicationType.NONE);
    		}
    		application.setApplicationContextFactory(getApplicationContextFactory(mergedConfig));
    		if (mergedConfig.getParent() != null) {
    			application.setBannerMode(Banner.Mode.OFF);
    		}
    		application.setInitializers(initializers);
    		ConfigurableEnvironment environment = getEnvironment();
    		if (environment != null) {
    			prepareEnvironment(mergedConfig, application, environment, false);
    			application.setEnvironment(environment);
    		}
    		else {
    			application.addListeners(new PrepareEnvironmentListener(mergedConfig));
    		}
    	}
}
