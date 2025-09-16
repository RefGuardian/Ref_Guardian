public class test234 {

    @Override
	public ConditionOutcome getMatchOutcome(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		boolean match = false;
		List<ConditionMessage> messages = extractMessages(ConditionalOnGraphQlSchema.class, conditionContext);
		ConditionOutcome outcome = processSchemaResources(match, messages, conditionContext, annotatedTypeMetadata);
		return outcome;
	}
	
	private List<ConditionMessage> extractMessages(Class<?> annotationClass, ConditionContext context) {
		List<ConditionMessage> messages = new ArrayList<>(2);
		ConditionMessage.Builder message = ConditionMessage.forCondition(annotationClass);
		Binder binder = Binder.get(context.getEnvironment());
		GraphQlProperties.Schema schema = binder.bind("spring.graphql.schema", GraphQlProperties.Schema.class)
			.orElse(new GraphQlProperties.Schema());
		ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils
			.getResourcePatternResolver(context.getResourceLoader());
		List<Resource> schemaResources = resolveSchemaResources(resourcePatternResolver, schema.getLocations(),
				schema.getFileExtensions());
		if (!schemaResources.isEmpty()) {
			match = true;
			messages.add(message.found("schema", "schemas").items(ConditionMessage.Style.QUOTE, schemaResources));
		}
		else {
			messages.add(message.didNotFind("schema files in locations")
				.items(ConditionMessage.Style.QUOTE, Arrays.asList(schema.getLocations())));
		}
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		String[] customizerBeans = beanFactory.getBeanNamesForType(GraphQlSourceBuilderCustomizer.class, false, false);
		if (customizerBeans.length != 0) {
			match = true;
			messages.add(message.found("customizer", "customizers").items(Arrays.asList(customizerBeans)));
		}
		else {
			messages.add((message.didNotFind("GraphQlSourceBuilderCustomizer").atAll()));
		}
		String[] graphQlSourceBeanNames = beanFactory.getBeanNamesForType(GraphQlSource.class, false, false);
		if (graphQlSourceBeanNames.length != 0) {
			match = true;
			messages.add(message.found("GraphQlSource").items(Arrays.asList(graphQlSourceBeanNames)));
		}
		else {
			messages.add((message.didNotFind("GraphQlSource").atAll()));
		}
		return messages;
	}
	
	private ConditionOutcome processSchemaResources(boolean match, List<ConditionMessage> messages, ConditionContext context, AnnotatedTypeMetadata metadata) {
		return new ConditionOutcome(match, ConditionMessage.of(messages));
	}
}
