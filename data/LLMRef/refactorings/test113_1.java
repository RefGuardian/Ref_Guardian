public class test113 {

    private static final String RSOCKET_SERVER_TRANSPORT = "spring.rsocket.server.transport";
    private static final String RSOCKET_SERVER_MAPPING_PATH = "spring.rsocket.server.mapping-path";

    @Test
    void webEndpointsShouldWork() {
        new ReactiveWebApplicationContextRunner(AnnotationConfigReactiveWebServerApplicationContext::new)
            .withConfiguration(AutoConfigurations.of(HttpHandlerAutoConfiguration.class, WebFluxAutoConfiguration.class,
                ErrorWebFluxAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class,
                JacksonAutoConfiguration.class, CodecsAutoConfiguration.class,
                RSocketStrategiesAutoConfiguration.class, RSocketServerAutoConfiguration.class,
                RSocketMessagingAutoConfiguration.class, RSocketRequesterAutoConfiguration.class))
            .withUserConfiguration(WebConfiguration.class)
            .withPropertyValues(RSOCKET_SERVER_TRANSPORT + "=websocket",
                RSOCKET_SERVER_MAPPING_PATH + "=/rsocket")
            .run((context) -> {
                ReactiveWebServerApplicationContext serverContext = (ReactiveWebServerApplicationContext) context
                    .getSourceApplicationContext();
                RSocketRequester requester = createRSocketRequester(context, serverContext.getWebServer());
                TestProtocol rsocketResponse = requester.route("websocket")
                    .data(new TestProtocol("rsocket"))
                    .retrieveMono(TestProtocol.class)
                    .block(Duration.ofSeconds(3));
                assertThat(rsocketResponse.getName()).isEqualTo("rsocket");
                WebTestClient client = createWebTestClient(serverContext.getWebServer());
                client.get()
                    .uri("/protocol")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath("name")
                    .isEqualTo("http");
            });
    }
}
