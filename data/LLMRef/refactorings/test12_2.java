public class test12 {

    private void configureContext(C context, boolean refresh) {
        applyParent(context);
        applyClassLoader(context);
        this.runnerConfiguration.environmentProperties.applyTo(context);
        this.runnerConfiguration.beanRegistrations.forEach((registration) -> registration.apply(context));
        this.runnerConfiguration.initializers.forEach((initializer) -> initializer.initialize(context));
        BiConsumer<Class<?>, String> registrar = getRegistrar(context);
        registerConfigurations(registrar);
        if (refresh) {
            context.refresh();
        }
    }

    private void applyParent(C context) {
        if (this.runnerConfiguration.parent != null) {
            context.setParent(this.runnerConfiguration.parent);
        }
    }

    private void applyClassLoader(C context) {
        if (this.runnerConfiguration.classLoader != null) {
            Assert.isInstanceOf(DefaultResourceLoader.class, context);
            ((DefaultResourceLoader) context).setClassLoader(this.runnerConfiguration.classLoader);
        }
    }

    private void registerConfigurations(BiConsumer<Class<?>, String> registrar) {
        if (!CollectionUtils.isEmpty(this.runnerConfiguration.configurations)) {
            for (Configurations configurations : Configurations.collate(this.runnerConfiguration.configurations)) {
                for (Class<?> beanClass : Configurations.getClasses(configurations)) {
                    String beanName = configurations.getBeanName(beanClass);
                    registrar.accept(beanClass, beanName);
                }
            }
        }
    }
}
