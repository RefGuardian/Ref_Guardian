public class test128 {

    private static final String GRAPHQL_MAPPING_PATH = "graphql";
    private static final String RS_SOCKET_MAPPING_PATH = "/rsocket";

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
                "spring.graphql.rsocket.mapping=" + GRAPHQL_MAPPING_PATH, "spring.rsocket.server.transport=websocket",
                "spring.rsocket.server.mapping-path=" + RS_SOCKET_MAPPING_PATH);
        contextRunner.run((context) -> {
            String serverPort = context.getEnvironment().getProperty("local.server.port");
            RSocketGraphQlClient client = RSocketGraphQlClient.builder()
                .webSocket(URI.create("ws://localhost:" + serverPort + RS_SOCKET_MAPPING_PATH))
                .route(GRAPHQL_MAPPING_PATH)
                .build();
            consumer.accept(client);
        });
    }
}
