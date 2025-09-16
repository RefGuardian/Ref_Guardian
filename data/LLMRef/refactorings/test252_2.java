public class test252 {

    /**
     * Scan for entities with the specified annotations.
     * @param annotationTypes the annotation types used on the entities
     * @return a set of entity classes
     * @throws ClassNotFoundException if an entity class cannot be loaded
     */
    @SafeVarargs
    public final Set<Class<?>> scan(Class<? extends Annotation>... annotationTypes) throws ClassNotFoundException {
        List<String> packages = getPackages();
        if (packages.isEmpty()) {
            return Collections.emptySet();
        }
        ClassPathScanningCandidateComponentProvider scanner = createClassPathScanningCandidateComponentProvider(
                this.context);
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            scanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        }
        Set<Class<?>> entitySet = new HashSet<>();
        for (String basePackage : packages) {
            if (StringUtils.hasText(basePackage)) {
                for (BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
                    entitySet.add(ClassUtils.forName(candidate.getBeanClassName(), this.context.getClassLoader()));
                }
            }
        }
        return entitySet;
    }

    private List<String> getPackages() {
        // logic to get packages
    }
}
