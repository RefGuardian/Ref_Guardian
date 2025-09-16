public class test252 {

    private static final String[] PACKAGES = getPackages();

    /**
     * Scan for entities with the specified annotations.
     * @param annotationTypes the annotation types used on the entities
     * @return a set of entity classes
     * @throws ClassNotFoundException if an entity class cannot be loaded
     */
    @SafeVarargs
    public final Set<Class<?>> scan(Class<? extends Annotation>... annotationTypes) throws ClassNotFoundException {
        if (PACKAGES.length == 0) {
            return Collections.emptySet();
        }
        ClassPathScanningCandidateComponentProvider scanner = createClassPathScanningCandidateComponentProvider(
                this.context);
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            scanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        }
        Set<Class<?>> entitySet = new HashSet<>();
        for (String basePackage : PACKAGES) {
            if (StringUtils.hasText(basePackage)) {
                for (BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
                    entitySet.add(ClassUtils.forName(candidate.getBeanClassName(), this.context.getClassLoader()));
                }
            }
        }
        return entitySet;
    }
}
