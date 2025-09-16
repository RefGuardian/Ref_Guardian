public class test217 {

    @Override
    	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    		MultiValueMap<String, Object> attributes = metadata
    			.getAllAnnotationAttributes(ConditionalOnResource.class.getName(), true);
    		ResourceLoader loader = context.getResourceLoader();
    		List<String> locations = new ArrayList<>();
    		collectValues(locations, attributes.get("resources"));
    		Assert.state(!locations.isEmpty(),
    				"@ConditionalOnResource annotations must specify at least one resource location");
    		List<String> missing = new ArrayList<>();
    		for (String location : locations) {
    			String resource = context.getEnvironment().resolvePlaceholders(location);
    			if (!loader.getResource(resource).exists()) {
    				missing.add(location);
    			}
    		}
    		if (!missing.isEmpty()) {
    			return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnResource.class)
    				.didNotFind("resource", "resources")
    				.items(Style.QUOTE, missing));
    		}
    		return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnResource.class)
    			.found("location", "locations")
    			.items(locations));
    	}
}
