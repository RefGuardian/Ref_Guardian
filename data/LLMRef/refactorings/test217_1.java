public class test217 {

    @Override
    	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    		MultiValueMap<String, Object> attributes = metadata
    			.getAllAnnotationAttributes(ConditionalOnResource.class.getName(), true);
    		ResourceLoader loader = context.getResourceLoader();
    		List<String> locations = extractLocations(attributes);
    		assertLocationsNotEmpty(locations);
    		List<String> missing = findMissingResources(context, loader, locations);
    		if (!missing.isEmpty()) {
    			return createNoMatchOutcome(ConditionalOnResource.class, missing);
    		}
    		return createMatchOutcome(ConditionalOnResource.class, locations);
    	}

    private List<String> extractLocations(MultiValueMap<String, Object> attributes) {
		List<String> locations = new ArrayList<>();
		collectValues(locations, attributes.get("resources"));
		return locations;
	}

	private void assertLocationsNotEmpty(List<String> locations) {
		Assert.state(!locations.isEmpty(), "@ConditionalOnResource annotations must specify at least one resource location");
	}

	private List<String> findMissingResources(ConditionContext context, ResourceLoader loader, List<String> locations) {
		List<String> missing = new ArrayList<>();
		for (String location : locations) {
			String resource = context.getEnvironment().resolvePlaceholders(location);
			if (!loader.getResource(resource).exists()) {
				missing.add(location);
			}
		}
		return missing;
	}

	private ConditionOutcome createNoMatchOutcome(Class<?> conditionClass, List<String> missing) {
		return ConditionOutcome.noMatch(ConditionMessage.forCondition(conditionClass)
			.didNotFind("resource", "resources")
			.items(Style.QUOTE, missing));
	}

	private ConditionOutcome createMatchOutcome(Class<?> conditionClass, List<String> locations) {
		return ConditionOutcome.match(ConditionMessage.forCondition(conditionClass)
			.found("location", "locations")
			.items(locations));
	}
}
