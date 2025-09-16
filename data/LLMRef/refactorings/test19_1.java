public class test19 {

    private void resetMocks(ConfigurableApplicationContext applicationContext, MockReset reset) {
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        resetSingletonMocks(beanFactory, reset);
        resetMockitoBeans(beanFactory, reset);
        if (applicationContext.getParent() != null) {
            resetMocks(applicationContext.getParent(), reset);
        }
    }

    private void resetSingletonMocks(ConfigurableListableBeanFactory beanFactory, MockReset reset) {
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

    private void resetMockitoBeans(ConfigurableListableBeanFactory beanFactory, MockReset reset) {
        try {
            MockitoBeans mockedBeans = beanFactory.getBean(MockitoBeans.class);
            for (Object mockedBean : mockedBeans) {
                if (reset.equals(MockReset.get(mockedBean))) {
                    Mockito.reset(mockedBean);
                }
            }
        }
        catch (NoSuchBeanDefinitionException ex) {
            // Continue
        }
    }

}
