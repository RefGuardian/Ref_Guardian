public class test208 {

	protected Set<String> getExclusions(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		Set<String> exclusions = new LinkedHashSet<>();
		Class<?> source = ClassUtils.resolveClassName(metadata.getClassName(), getBeanClassLoader());
		for (String annotationName : ANNOTATION_NAMES) {
			AnnotationAttributes merged = AnnotatedElementUtils.getMergedAnnotationAttributes(source, annotationName);
			Class<?>[] exclude = (merged != null) ? merged.getClassArray("exclude") : null;
			if (exclude != null) {
				for (Class<?> excludeClass : exclude) {
					exclusions.add(excludeClass.getName());
				}
			}
		}
		for (List<Annotation> annotations : getAnnotations(metadata).values()) {
			for (Annotation annotation : annotations) {
				String[] exclude = (String[]) AnnotationUtils.getAnnotationAttributes(annotation, true).get("exclude");
				if (!ObjectUtils.isEmpty(exclude)) {
					exclusions.addAll(Arrays.asList(exclude));
				}
			}
		}
		exclusions.addAll(getExcludeAutoConfigurationsProperty());
		return exclusions;
	}

}
