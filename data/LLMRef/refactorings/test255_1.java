public class test255 {

    private static final String HOSTNAME = "hostname";
    private static final String PATH_PREFIX = "pathPrefix";

    @Bean
    RestClientBuilder elasticsearchRestClientBuilder(ElasticsearchConnectionDetails connectionDetails,
            ObjectProvider<RestClientBuilderCustomizer> builderCustomizers) {
        HttpHost[] nodes = connectionDetails.getNodes()
                .stream()
                .map((node) -> new HttpHost(node.hostname(), node.port(), node.protocol().getScheme()))
                .toArray(HttpHost[]::new);
        RestClientBuilder builder = RestClient.builder(nodes);
        builder.setHttpClientConfigCallback((httpClientBuilder) -> {
            builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(httpClientBuilder));
            SslBundle sslBundle = connectionDetails.getSslBundle();
            if (sslBundle != null) {
                configureSsl(httpClientBuilder, sslBundle);
            }
            return httpClientBuilder;
        });
        builder.setRequestConfigCallback((requestConfigBuilder) -> {
            builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(requestConfigBuilder));
            return requestConfigBuilder;
        });
        String pathPrefix = connectionDetails.getPathPrefix();
        if (pathPrefix != null) {
            builder.setPathPrefix(pathPrefix);
        }
        builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        return builder;
    }
}
