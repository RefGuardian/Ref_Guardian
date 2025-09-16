public class test224 {

    Spec(ConditionContext context, AnnotatedTypeMetadata metadata, MergedAnnotations annotations,
    				Class<A> annotationType) {
    			MultiValueMap<String, Object> attributes = extractAttributes(annotations, annotationType);
    			MergedAnnotation<A> annotation = annotations.get(annotationType);
    			this.context = context;
    			this.annotationType = annotationType;
    			this.names = extract(attributes, "name");
    			this.annotations = extract(attributes, "annotation");
    			this.ignoredTypes = resolveWhenPossible(extract(attributes, "ignored", "ignoredType"));
    			this.parameterizedContainers = resolveWhenPossible(extract(attributes, "parameterizedContainer"));
    			this.strategy = getSearchStrategy(annotation);
    			Set<ResolvableType> types = resolveWhenPossible(extractTypes(attributes));
    			BeanTypeDeductionException deductionException = getDeductionException(types);
    			this.types = types;
    			validate(deductionException);
    		}

    private MultiValueMap<String, Object> extractAttributes(MergedAnnotations annotations, Class<A> annotationType) {
        return annotations.stream(annotationType)
            .filter(MergedAnnotationPredicates.unique(MergedAnnotation::getMetaTypes))
            .collect(MergedAnnotationCollectors.toMultiValueMap(Adapt.CLASS_TO_STRING));
    }

    private SearchStrategy getSearchStrategy(MergedAnnotation<A> annotation) {
        return annotation.getValue("search", SearchStrategy.class).orElse(null);
    }

    private BeanTypeDeductionException getDeductionException(Set<ResolvableType> types) {
        if (types.isEmpty() && this.names.isEmpty() && this.annotations.isEmpty()) {
            try {
                types = deducedBeanType(context, metadata);
            }
            catch (BeanTypeDeductionException ex) {
                return ex;
            }
        }
        return null;
    }
}
