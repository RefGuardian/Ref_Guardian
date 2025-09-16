public class test19 {

	private void resetMocks(ConfigurableApplicationContext applicationContext, MockReset reset) {
		resetSingletonBeans(applicationContext, reset);
		resetMockedBeans(applicationContext);
		if (applicationContext.getParent() != null) {
			resetMocks(applicationContext.getParent(), reset);
		}
	}

	private void resetSingletonBeans(ConfigurableApplicationContext applicationContext, MockReset reset) {
		ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
		String[] names = beanFactory.getBeanDefinitionNames();
		Set<String> instantiatedSingletons = new HashSet<>(Arrays.asList(beanFactory.getSingletonNames()));
		for (String name : names) {
			BeanDefinition definition = beanFactory.getBeanDefinition(name);
			if (definition.isSingleton() && instantiatedSingletons.contains(name)) {
				Object bean = getBean(beanFactory, name);
				if (bean != null && reset.equals(MockReset.get(bean))) {
					Mockito.reset(bean);
				}
			}
		}
	}

	private void resetMockedBeans(ConfigurableApplicationContext applicationContext) {
		try {
			MockitoBeans mockedBeans = applicationContext.getBean(MockitoBeans.class);
			for (Object mockedBean : mockedBeans) {
				if (reset.equals(MockReset.get(mockedBean))) {
					Mockito.reset(mockedBean);
				}
			}
		} catch (NoSuchBeanDefinitionException ex) {
			// Continue
		}
	}

}
