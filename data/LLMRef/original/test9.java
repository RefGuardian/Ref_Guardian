public class test9 {

    private void assertCompiledTest(Class<?> testClass) throws Exception {
    		try {
    			System.setProperty(AotDetector.AOT_ENABLED, "true");
    			resetAotClasses();
    			AotTestContextInitializers aotContextInitializers = new AotTestContextInitializers();
    			TestContextBootstrapper testContextBootstrapper = BootstrapUtils.resolveTestContextBootstrapper(testClass);
    			MergedContextConfiguration mergedConfig = testContextBootstrapper.buildMergedContextConfiguration();
    			ApplicationContextInitializer<ConfigurableApplicationContext> contextInitializer = aotContextInitializers
    				.getContextInitializer(testClass);
    			ConfigurableApplicationContext context = (ConfigurableApplicationContext) ((AotContextLoader) mergedConfig
    				.getContextLoader()).loadContextForAotRuntime(mergedConfig, contextInitializer);
    			assertThat(context).isExactlyInstanceOf(GenericApplicationContext.class);
    			String[] beanNames = context.getBeanNamesForType(ExampleBean.class);
    			BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition(beanNames[0]);
    			assertThat(beanDefinition).isNotExactlyInstanceOf(GenericBeanDefinition.class);
    		}
    		finally {
    			System.clearProperty(AotDetector.AOT_ENABLED);
    			resetAotClasses();
    		}
    	}
}
