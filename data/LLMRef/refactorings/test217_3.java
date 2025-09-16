public class test217 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(ConditionalOnResource.class.getName(), true);
        ResourceLoader loader = context.getResourceLoader();
        List<String> locations = extractLocations(attributes);
        checkLocationsNotEmpty(locations);
        List<String> missing = findMissingResources(context, loader, locations);
        return handleMissingResources(missing, locations);
    }

    private List<String> extractLocations(MultiValueMap<String, Object> attributes) {
        List<String> locations = new ArrayList<>();
        collectValues(locations, attributes.get("resources"));
        return locations;
    }

    private void checkLocationsNotEmpty(List<String> locations) {
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

    private ConditionOutcome handleMissingResources(List<String> missing, List<String> locations) {
        if (!missing.isEmpty()) {
            return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnResource.class)
                    .didNotFind("resource", "resources")
                    .items(Style.QUOTE, missing));
        } else {
            return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnResource.class)
                    .found("location", "locations")
                    .items(locations));
        }
    }
}
