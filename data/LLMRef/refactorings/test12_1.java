public class test12 {

    private void configureContext(C context, boolean refresh) {
        configureParentContext(context);
        configureClassLoader(context);
        applyEnvironmentProperties(context);
        applyBeanRegistrations(context);
        initializeInitializers(context);
        configureConfigurations(context);
        if (refresh) {
            context.refresh();
        }
    }

    private void configureParentContext(C context) {
        if (this.runnerConfiguration.parent != null) {
            context.setParent(this.runnerConfiguration.parent);
        }
    }

    private void configureClassLoader(C context) {
        if (this.runnerConfiguration.classLoader != null) {
            Assert.isInstanceOf(DefaultResourceLoader.class, context);
            ((DefaultResourceLoader) context).setClassLoader(this.runnerConfiguration.classLoader);
        }
    }

    private void applyEnvironmentProperties(C context) {
        this.runnerConfiguration.environmentProperties.applyTo(context);
    }

    private void applyBeanRegistrations(C context) {
        this.runnerConfiguration.beanRegistrations.forEach((registration) -> registration.apply(context));
    }

    private void initializeInitializers(C context) {
        this.runnerConfiguration.initializers.forEach((initializer) -> initializer.initialize(context));
    }

    private void configureConfigurations(C context) {
        if (!CollectionUtils.isEmpty(this.runnerConfiguration.configurations)) {
            BiConsumer<Class<?>, String> registrar = getRegistrar(context);
            for (Configurations configurations : Configurations.collate(this.runnerConfiguration.configurations)) {
                for (Class<?> beanClass : Configurations.getClasses(configurations)) {
                    String beanName = configurations.getBeanName(beanClass);
                    registrar.accept(beanClass, beanName);
                }
            }
        }
    }
}
