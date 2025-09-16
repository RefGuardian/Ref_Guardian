public class test224 {

    Spec(ConditionContext context, AnnotatedTypeMetadata metadata, MergedAnnotations annotations,
    				Class<A> annotationType) {
    			MultiValueMap<String, Object> attributes = annotations.stream(annotationType)
    				.filter(MergedAnnotationPredicates.unique(MergedAnnotation::getMetaTypes))
    				.collect(MergedAnnotationCollectors.toMultiValueMap(Adapt.CLASS_TO_STRING));
    			MergedAnnotation<A> annotation = annotations.get(annotationType);
    			this.context = context;
    			this.annotationType = annotationType;
    			this.names = extract(attributes, "name");
    			this.annotations = extract(attributes, "annotation");
    			this.ignoredTypes = resolveWhenPossible(extract(attributes, "ignored", "ignoredType"));
    			this.parameterizedContainers = resolveWhenPossible(extract(attributes, "parameterizedContainer"));
    			this.strategy = annotation.getValue("search", SearchStrategy.class).orElse(null);
    			Set<ResolvableType> types = resolveWhenPossible(extractTypes(attributes));
    			BeanTypeDeductionException deductionException = null;
    			if (types.isEmpty() && this.names.isEmpty() && this.annotations.isEmpty()) {
    				try {
    					types = deducedBeanType(context, metadata);
    				}
    				catch (BeanTypeDeductionException ex) {
    					deductionException = ex;
    				}
    			}
    			this.types = types;
    			validate(deductionException);
    		}
}
