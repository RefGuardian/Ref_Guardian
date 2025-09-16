public class test224 {

    Spec(ConditionContext context, AnnotatedTypeMetadata metadata, MergedAnnotations annotations,
    				Class<A> annotationType) {
    			Set<String> names = getNames(annotations, annotationType);
    			Set<Annotation> annotationSet = getAnnotations(annotations, annotationType);
    			Set<String> ignoredTypes = resolveIgnoredTypes(annotations, annotationType);
    			Set<String> parameterizedContainers = resolveParameterizedContainers(annotations);
    			SearchStrategy strategy = getSearchStrategy(annotations, annotationType);
    			Set<ResolvableType> types = resolveTypes(annotations);
    			BeanTypeDeductionException deductionException = null;
    			if (types.isEmpty() && names.isEmpty() && annotationSet.isEmpty()) {
    				try {
    					types = deducedBeanType(context, metadata);
    				}
    				catch (BeanTypeDeductionException ex) {
    					deductionException = ex;
    				}
    			}
    			this.context = context;
    			this.annotationType = annotationType;
    			this.names = names;
    			this.annotations = annotationSet;
    			this.ignoredTypes = ignoredTypes;
    			this.parameterizedContainers = parameterizedContainers;
    			this.strategy = strategy;
    			this.types = types;
    			validate(deductionException);
    		}

    private Set<String> getNames(MergedAnnotations annotations, Class<A> annotationType) {
        return annotations.stream(annotationType)
            .filter(MergedAnnotationPredicates.unique(MergedAnnotation::getMetaTypes))
            .collect(MergedAnnotationCollectors.toMultiValueMap(Adapt.CLASS_TO_STRING))
            .get("name");
    }

    private Set<Annotation> getAnnotations(MergedAnnotations annotations, Class<A> annotationType) {
        return annotations.stream(annotationType)
            .filter(MergedAnnotationPredicates.unique(MergedAnnotation::getMetaTypes))
            .collect(MergedAnnotationCollectors.toMultiValueMap(Adapt.CLASS_TO_STRING))
            .get("annotation");
    }

    private Set<String> resolveIgnoredTypes(MergedAnnotations annotations, Class<A> annotationType) {
        return resolveWhenPossible(annotations.stream(annotationType).collect(MergedAnnotationCollectors.toMultiValueMap(Adapt.CLASS_TO_STRING)).get("ignored", "ignoredType"));
    }

    private Set<String> resolveParameterizedContainers(MergedAnnotations annotations) {
        return resolveWhenPossible(annotations.stream(annotationType).collect(MergedAnnotationCollectors.toMultiValueMap(Adapt.CLASS_TO_STRING)).get("parameterizedContainer"));
    }

    private SearchStrategy getSearchStrategy(MergedAnnotations annotations, Class<A> annotationType) {
        return annotations.get(annotationType)
            .getValue("search", SearchStrategy.class).orElse(null);
    }

    private Set<ResolvableType> resolveTypes(MergedAnnotations annotations) {
        return resolveWhenPossible(extractTypes(annotations.stream(annotationType).collect(MergedAnnotationCollectors.toMultiValueMap(Adapt.CLASS_TO_STRING))));
    }
}
