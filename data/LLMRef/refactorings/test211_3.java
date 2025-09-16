public class test211 {

    /**
    	 * Get the provider that can be used to render the given view.
    	 * @param view the view to render
    	 * @param environment the environment
    	 * @param classLoader the class loader
    	 * @param resourceLoader the resource loader
    	 * @return a {@link TemplateAvailabilityProvider} or null
    	 */
    	public TemplateAvailabilityProvider getProvider(String view, Environment environment, ClassLoader classLoader,
    			ResourceLoader resourceLoader) {
    		Assert.notNull(view, "'view' must not be null");
    		Assert.notNull(environment, "'environment' must not be null");
    		Assert.notNull(classLoader, "'classLoader' must not be null");
    		Assert.notNull(resourceLoader, "'resourceLoader' must not be null");
    		Boolean useCache = environment.getProperty("spring.template.provider.cache", Boolean.class, true);
    		if (!useCache) {
    			return findProvider(view, environment, classLoader, resourceLoader);
    		}
    		TemplateAvailabilityProvider provider = getTemplateProvider(view, environment, classLoader, resourceLoader);
    		provider = (provider != null) ? provider : NONE;
    		return (provider != NONE) ? provider : null;
    	}

	private TemplateAvailabilityProvider getTemplateProvider(String view, Environment environment, ClassLoader classLoader,
			ResourceLoader resourceLoader) {
		synchronized (this.cache) {
			TemplateAvailabilityProvider provider = findProvider(view, environment, classLoader, resourceLoader);
			this.resolved.put(view, provider);
			this.cache.put(view, provider);
			return provider;
		}
	}
}
