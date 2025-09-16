public class test14 {

    private void configure(MergedContextConfiguration mergedConfig, SpringApplication application) {
        setMainApplicationClass(mergedConfig, application);
        addPrimarySources(mergedConfig, application);
        addAllSources(mergedConfig, application);
        List<ApplicationContextInitializer<?>> initializers = getInitializers(mergedConfig, application);
        addWebApplicationType(mergedConfig, application, initializers);
        setApplicationContextFactory(mergedConfig, application);
        setBannerMode(mergedConfig, application);
        setInitializers(application, initializers);
        prepareEnvironment(mergedConfig, application);
    }

    private void setMainApplicationClass(MergedContextConfiguration mergedConfig, SpringApplication application) {
        application.setMainApplicationClass(mergedConfig.getTestClass());
    }

    private void addPrimarySources(MergedContextConfiguration mergedConfig, SpringApplication application) {
        application.addPrimarySources(Arrays.asList(mergedConfig.getClasses()));
    }

    private void addAllSources(MergedContextConfiguration mergedConfig, SpringApplication application) {
        application.getSources().addAll(Arrays.asList(mergedConfig.getLocations()));
    }

    private void addWebApplicationType(MergedContextConfiguration mergedConfig, SpringApplication application, List<ApplicationContextInitializer<?>> initializers) {
        if (mergedConfig instanceof WebMergedContextConfiguration) {
            application.setWebApplicationType(WebApplicationType.SERVLET);
            if (!isEmbeddedWebEnvironment(mergedConfig)) {
                new WebConfigurer().configure(mergedConfig, initializers);
            }
        } else if (mergedConfig instanceof ReactiveWebMergedContextConfiguration) {
            application.setWebApplicationType(WebApplicationType.REACTIVE);
        } else {
            application.setWebApplicationType(WebApplicationType.NONE);
        }
    }

    private void setApplicationContextFactory(MergedContextConfiguration mergedConfig, SpringApplication application) {
        application.setApplicationContextFactory(getApplicationContextFactory(mergedConfig));
    }

    private void setBannerMode(MergedContextConfiguration mergedConfig, SpringApplication application) {
        if (mergedConfig.getParent() != null) {
            application.setBannerMode(Banner.Mode.OFF);
        }
    }

    private void setInitializers(SpringApplication application, List<ApplicationContextInitializer<?>> initializers) {
        application.setInitializers(initializers);
    }

    private void prepareEnvironment(MergedContextConfiguration mergedConfig, SpringApplication application) {
        ConfigurableEnvironment environment = getEnvironment();
        if (environment != null) {
            prepareEnvironment(mergedConfig, application, environment, false);
            application.setEnvironment(environment);
        } else {
            application.addListeners(new PrepareEnvironmentListener(mergedConfig));
        }
    }
}
