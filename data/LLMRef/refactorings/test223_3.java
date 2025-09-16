public class test223 {

    private String[] getBeanNamesForAnnotation(ListableBeanFactory beanFactory,
            Class<? extends Annotation> annotationType) {
        Set<String> foundBeanNames = new LinkedHashSet<>();
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            if (beanFactory instanceof ConfigurableListableBeanFactory configurableListableBeanFactory) {
                BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName);
                if (beanDefinition != null && beanDefinition.isAbstract()) {
                    continue;
                }
            }
            if (beanFactory.findAnnotationOnBean(beanName, annotationType, false) != null) {
                foundBeanNames.add(beanName);
            }
        }
        if (beanFactory instanceof SingletonBeanRegistry singletonBeanRegistry) {
            for (String beanName : singletonBeanRegistry.getSingletonNames()) {
                if (beanFactory.findAnnotationOnBean(beanName, annotationType) != null) {
                    foundBeanNames.add(beanName);
                }
            }
        }
        return foundBeanNames.toArray(String[]::new);
    }

}
