public class test217 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attributes = extractAttributes(metadata);
        ResourceLoader loader = context.getResourceLoader();
        List<String> locations = extractResourceLocations(attributes.get("resources"));
        Assert.state(!locations.isEmpty(),
                "@ConditionalOnResource annotations must specify at least one resource location");
        List<String> missing = findMissingResources(context, loader, locations);
        if (!missing.isEmpty()) {
            return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnResource.class)
                    .didNotFind("resource", "resources")
                    .items(Style.QUOTE, missing));
        }
        return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnResource.class)
                .found("location", "locations")
                .items(locations));
    }

    private MultiValueMap<String, Object> extractAttributes(AnnotatedTypeMetadata metadata) {
        return metadata.getAllAnnotationAttributes(ConditionalOnResource.class.getName(), true);
    }

    private List<String> extractResourceLocations(List<Object> resources) {
        List<String> locations = new ArrayList<>();
        collectValues(locations, resources);
        return locations;
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
}
