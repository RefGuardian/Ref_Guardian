public class test113 {

    private static final String TRANSPORT_TYPE = "websocket";
    private static final String MAPPING_PATH = "/rsocket";

    @Test
    void webEndpointsShouldWork() {
        new ReactiveWebApplicationContextRunner(AnnotationConfigReactiveWebServerApplicationContext::new)
                .withConfiguration(AutoConfigurations.of(HttpHandlerAutoConfiguration.class, WebFluxAutoConfiguration.class,
                        ErrorWebFluxAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class,
                        JacksonAutoConfiguration.class, CodecsAutoConfiguration.class,
                        RSocketStrategiesAutoConfiguration.class, RSocketServerAutoConfiguration.class,
                        RSocketMessagingAutoConfiguration.class, RSocketRequesterAutoConfiguration.class))
                .withUserConfiguration(WebConfiguration.class)
                .withPropertyValues("spring.rsocket.server.transport=" + TRANSPORT_TYPE,
                        "spring.rsocket.server.mapping-path=" + MAPPING_PATH)
                .run((context) -> {
                    ReactiveWebServerApplicationContext serverContext = (ReactiveWebServerApplicationContext) context
                            .getSourceApplicationContext();
                    RSocketRequester requester = createRSocketRequester(context, serverContext.getWebServer());
                    TestProtocol rsocketResponse = requester.route(TRANSPORT_TYPE)
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
