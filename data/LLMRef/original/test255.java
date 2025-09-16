public class test255 {

    @Bean
    		RestClientBuilder elasticsearchRestClientBuilder(ElasticsearchConnectionDetails connectionDetails,
    				ObjectProvider<RestClientBuilderCustomizer> builderCustomizers) {
    			RestClientBuilder builder = RestClient.builder(connectionDetails.getNodes()
    				.stream()
    				.map((node) -> new HttpHost(node.hostname(), node.port(), node.protocol().getScheme()))
    				.toArray(HttpHost[]::new));
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
