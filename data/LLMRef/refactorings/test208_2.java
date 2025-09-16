public class test208 {

    @Override
    protected Set<String> getExclusions(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        Set<String> exclusions = new LinkedHashSet<>();
        Class<?> source = ClassUtils.resolveClassName(metadata.getClassName(), getBeanClassLoader());
        extractAnnotationExclusions(exclusions, source);
        extractAnnotationAttributesExclusions(exclusions, metadata);
        exclusions.addAll(getExcludeAutoConfigurationsProperty());
        return exclusions;
    }

    private void extractAnnotationExclusions(Set<String> exclusions, Class<?> source) {
        for (String annotationName : ANNOTATION_NAMES) {
            AnnotationAttributes merged = AnnotatedElementUtils.getMergedAnnotationAttributes(source, annotationName);
            Class<?>[] exclude = (merged != null) ? merged.getClassArray("exclude") : null;
            if (exclude != null) {
                for (Class<?> excludeClass : exclude) {
                    exclusions.add(excludeClass.getName());
                }
            }
        }
    }

    private void extractAnnotationAttributesExclusions(Set<String> exclusions, AnnotationMetadata metadata) {
        for (List<Annotation> annotations : getAnnotations(metadata).values()) {
            for (Annotation annotation : annotations) {
                String[] exclude = (String[]) AnnotationUtils.getAnnotationAttributes(annotation, true).get("exclude");
                if (!ObjectUtils.isEmpty(exclude)) {
                    exclusions.addAll(Arrays.asList(exclude));
                }
            }
        }
    }
}
