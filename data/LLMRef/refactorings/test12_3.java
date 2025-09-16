public class test12 {

    private void configureContext(C context, boolean refresh) {
        extractLocalVariableIfNotNull(context, this.runnerConfiguration.parent, RunnerConfiguration::getParent, Context::setParent);
        extractLocalVariableIfNotNull(context, this.runnerConfiguration.classLoader, RunnerConfiguration::getClassLoader, (c) -> ((DefaultResourceLoader) c).setClassLoader(this.runnerConfiguration.classLoader));
        this.runnerConfiguration.environmentProperties.applyTo(context);
        this.runnerConfiguration.beanRegistrations.forEach((registration) -> registration.apply(context));
        this.runnerConfiguration.initializers.forEach((initializer) -> initializer.initialize(context));
        if (!CollectionUtils.isEmpty(this.runnerConfiguration.configurations)) {
            BiConsumer<Class<?>, String> registrar = getRegistrar(context);
            for (Configurations configurations : Configurations.collate(this.runnerConfiguration.configurations)) {
                for (Class<?> beanClass : Configurations.getClasses(configurations)) {
                    String beanName = configurations.getBeanName(beanClass);
                    registrar.accept(beanClass, beanName);
                }
            }
        }
        if (refresh) {
            context.refresh();
        }
    }

    private void extractLocalVariableIfNotNull(C context, Object object, Supplier<Object> getter, Consumer<Object> setter) {
        if (object != null) {
            Object extractedObject = getter.get();
            setter.accept(extractedObject);
        }
    }
}
