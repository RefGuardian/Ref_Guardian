public class test233 {

    @Bean
    @ConditionalOnMissingBean
    public GraphQlSource graphQlSource(ResourcePatternResolver resourcePatternResolver, GraphQlProperties properties,
                                       ObjectProvider<DataFetcherExceptionResolver> exceptionResolvers,
                                       ObjectProvider<SubscriptionExceptionResolver> subscriptionExceptionResolvers,
                                       ObjectProvider<Instrumentation> instrumentations, ObjectProvider<RuntimeWiringConfigurer> wiringConfigurers,
                                       ObjectProvider<GraphQlSourceBuilderCustomizer> sourceCustomizers) {

        String[] schemaLocations = properties.getSchema().getLocations();
        List<Resource> schemaResources = new ArrayList<>();
        schemaResources.addAll(resolveSchemaResources(resourcePatternResolver, schemaLocations,
                properties.getSchema().getFileExtensions()));
        schemaResources.addAll(Arrays.asList(properties.getSchema().getAdditionalFiles()));

        GraphQlSource.SchemaResourceBuilder builder = createGraphQlSourceBuilder(properties, exceptionResolvers, subscriptionExceptionResolvers,
                instrumentations);
        if (properties.getSchema().getInspection().isEnabled()) {
            builder.inspectSchemaMappings(logger::info);
        }
        if (!properties.getSchema().getIntrospection().isEnabled()) {
            Introspection.enabledJvmWide(false);
        }
        builder.configureTypeDefinitions(new ConnectionTypeDefinitionConfigurer());
        wiringConfigurers.orderedStream().forEach(builder::configureRuntimeWiring);
        sourceCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        return builder.build();
    }

    private GraphQlSource.SchemaResourceBuilder createGraphQlSourceBuilder(GraphQlProperties properties, ObjectProvider<DataFetcherExceptionResolver> exceptionResolvers,
                                                                            ObjectProvider<SubscriptionExceptionResolver> subscriptionExceptionResolvers, ObjectProvider<Instrumentation> instrumentations) {
        return GraphQlSource.schemaResourceBuilder()
                .schemaResources(properties.getSchema().getSchemaResources())
                .exceptionResolvers(exceptionResolvers.orderedStream().toList())
                .subscriptionExceptionResolvers(subscriptionExceptionResolvers.orderedStream().toList())
                .instrumentation(instrumentations.orderedStream().toList());
    }
}
