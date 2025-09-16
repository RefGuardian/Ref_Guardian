public class test12 {

    private void configureContext(C context, boolean refresh) {
    		if (this.runnerConfiguration.parent != null) {
    			context.setParent(this.runnerConfiguration.parent);
    		}
    		if (this.runnerConfiguration.classLoader != null) {
    			Assert.isInstanceOf(DefaultResourceLoader.class, context);
    			((DefaultResourceLoader) context).setClassLoader(this.runnerConfiguration.classLoader);
    		}
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
}
