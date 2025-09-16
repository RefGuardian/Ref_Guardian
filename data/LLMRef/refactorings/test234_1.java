public class test234 {

    @Override
    	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    		boolean match = false;
    		List<ConditionMessage> messages = new ArrayList<>(2);
    		ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnGraphQlSchema.class);
    		Binder binder = Binder.get(context.getEnvironment());
    		GraphQlProperties.Schema schema = getGraphQlSchema(context, binder);
    		if (!schema.getLocations().isEmpty()) {
    			match = true;
    			messages.add(message.found("schema", "schemas").items(ConditionMessage.Style.QUOTE, resolveSchemaResources(context, schema)));
    		}
    		else {
    			messages.add(message.didNotFind("schema files in locations")
    				.items(ConditionMessage.Style.QUOTE, Arrays.asList(schema.getLocations())));
    		}
    		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    		if (hasCustomizerBeans(beanFactory)) {
    			match = true;
    			messages.add(message.found("customizer", "customizers").items(Arrays.asList(getCustomizerBeanNames(beanFactory))));
    		}
    		else {
    			messages.add((message.didNotFind("GraphQlSourceBuilderCustomizer").atAll()));
    		}
    		if (hasGraphQlSourceBeans(beanFactory)) {
    			match = true;
    			messages.add(message.found("GraphQlSource").items(Arrays.asList(getGraphQlSourceBeanNames(beanFactory))));
    		}
    		else {
    			messages.add((message.didNotFind("GraphQlSource").atAll()));
    		}
    		return new ConditionOutcome(match, ConditionMessage.of(messages));
    	}
	
	private GraphQlProperties.Schema getGraphQlSchema(ConditionContext context, Binder binder) {
		return binder.bind("spring.graphql.schema", GraphQlProperties.Schema.class).orElse(new GraphQlProperties.Schema());
	}

	private List<Resource> resolveSchemaResources(ConditionContext context, List<String> locations, List<String> fileExtensions) {
		ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(context.getResourceLoader());
		return resourcePatternResolver.getResources(String.join(",", locations) + "/*." + String.join(",", fileExtensions));
	}

	private boolean hasCustomizerBeans(ConfigurableListableBeanFactory beanFactory) {
		String[] customizerBeans = beanFactory.getBeanNamesForType(GraphQlSourceBuilderCustomizer.class, false, false);
		return customizerBeans.length != 0;
	}

	private String[] getCustomizerBeanNames(ConfigurableListableBeanFactory beanFactory) {
		return beanFactory.getBeanNamesForType(GraphQlSourceBuilderCustomizer.class, false, false);
	}

	private boolean hasGraphQlSourceBeans(ConfigurableListableBeanFactory beanFactory) {
		String[] graphQlSourceBeanNames = beanFactory.getBeanNamesForType(GraphQlSource.class, false, false);
		return graphQlSourceBeanNames.length != 0;
	}

	private String[] getGraphQlSourceBeanNames(ConfigurableListableBeanFactory beanFactory) {
		return beanFactory.getBeanNamesForType(GraphQlSource.class, false, false);
	}
}
