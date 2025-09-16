public class test222 {

    protected final MatchResult getMatchingBeans(Spec<?> spec) {
        ConfigurableListableBeanFactory beanFactory = getSearchBeanFactory(spec);
        ClassLoader classLoader = spec.getContext().getClassLoader();
        boolean considerHierarchy = spec.getStrategy() != SearchStrategy.CURRENT;
        Set<ResolvableType> parameterizedContainers = spec.getParameterizedContainers();
        MatchResult result = new MatchResult();
        Set<String> beansIgnoredByType = getNamesOfBeansIgnoredByType(beanFactory, considerHierarchy,
                spec.getIgnoredTypes(), parameterizedContainers);
        processTypes(spec, result, considerHierarchy, parameterizedContainers, beansIgnoredByType);
        processAnnotations(spec, result, classLoader, beanFactory, considerHierarchy, beansIgnoredByType);
        processBeanNames(spec, result, beanFactory, considerHierarchy, beansIgnoredByType);
        return result;
    }

    private void processTypes(Spec<?> spec, MatchResult result, boolean considerHierarchy, 
                              Set<ResolvableType> parameterizedContainers, Set<String> beansIgnoredByType) {
        for (ResolvableType type : spec.getTypes()) {
            Map<String, BeanDefinition> typeMatchedDefinitions = getBeanDefinitionsForType(beanFactory,
                    considerHierarchy, type, parameterizedContainers);
            Set<String> typeMatchedNames = matchedNamesFrom(typeMatchedDefinitions,
                    (name, definition) -> !ScopedProxyUtils.isScopedTarget(name)
                            && isCandidate(beanFactory, name, definition, beansIgnoredByType));
            if (typeMatchedNames.isEmpty()) {
                result.recordUnmatchedType(type);
            } else {
                result.recordMatchedType(type, typeMatchedNames);
            }
        }
    }

    private void processAnnotations(Spec<?> spec, MatchResult result, ClassLoader classLoader, 
                                    ConfigurableListableBeanFactory beanFactory, boolean considerHierarchy, 
                                    Set<String> beansIgnoredByType) {
        for (String annotation : spec.getAnnotations()) {
            Map<String, BeanDefinition> annotationMatchedDefinitions = getBeanDefinitionsForAnnotation(classLoader,
                    beanFactory, annotation, considerHierarchy);
            Set<String> annotationMatchedNames = matchedNamesFrom(annotationMatchedDefinitions,
                    (name, definition) -> isCandidate(beanFactory, name, definition, beansIgnoredByType));
            if (annotationMatchedNames.isEmpty()) {
                result.recordUnmatchedAnnotation(annotation);
            } else {
                result.recordMatchedAnnotation(annotation, annotationMatchedNames);
            }
        }
    }

    private void processBeanNames(Spec<?> spec, MatchResult result, ConfigurableListableBeanFactory beanFactory,
                                  boolean considerHierarchy, Set<String> beansIgnoredByType) {
        for (String beanName : spec.getNames()) {
            if (!beansIgnoredByType.contains(beanName) && containsBean(beanFactory, beanName, considerHierarchy)) {
                result.recordMatchedName(beanName);
            } else {
                result.recordUnmatchedName(beanName);
            }
        }
    }
}
