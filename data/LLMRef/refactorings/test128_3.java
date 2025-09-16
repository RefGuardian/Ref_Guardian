public class test128 {

    private void testWithRSocketWebSocket(Consumer<RSocketGraphQlClient> consumer) {
        ReactiveWebApplicationContextRunner contextRunner = new ReactiveWebApplicationContextRunner(
                AnnotationConfigReactiveWebServerApplicationContext::new)
                .withConfiguration(AutoConfigurations.of(HttpHandlerAutoConfiguration.class, WebFluxAutoConfiguration.class,
                        ErrorWebFluxAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class,
                        JacksonAutoConfiguration.class, RSocketStrategiesAutoConfiguration.class,
                        RSocketMessagingAutoConfiguration.class, RSocketServerAutoConfiguration.class,
                        GraphQlAutoConfiguration.class, GraphQlRSocketAutoConfiguration.class))
                .withInitializer(new ServerPortInfoApplicationContextInitializer())
                .withUserConfiguration(DataFetchersConfiguration.class, NettyServerConfiguration.class)
                .withPropertyValues("spring.main.web-application-type=reactive", "server.port=0",
                        "spring.graphql.rsocket.mapping=graphql", "spring.rsocket.server.transport=websocket",
                        "spring.rsocket.server.mapping-path=/rsocket");
        contextRunner.run((context) -> {
            String serverPort = context.getEnvironment().getProperty("local.server.port");
            RSocketGraphQlClient client = extractRSocketGraphQlClient(URI.create("ws://localhost:" + serverPort + "/rsocket"), "graphql");
            consumer.accept(client);
        });
    }

    private RSocketGraphQlClient extractRSocketGraphQlClient(URI uri, String route) {
        return RSocketGraphQlClient.builder()
                .webSocket(uri)
                .route(route)
                .build();
    }
}
