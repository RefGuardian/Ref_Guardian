public class test90 {

    @Test
    void defaultErrorView() {
        this.contextRunner
            .withPropertyValues("spring.mustache.prefix=classpath:/unknown/", "server.error.include-stacktrace=always",
                    "server.error.include-message=always")
            .run((context) -> {
                WebTestClient client = getWebClient(context);
                String body = getResponseBody(client);
                assertThat(body).contains("Whitelabel Error Page")
                    .contains(this.logIdFilter.getLogId())
                    .contains("<div>Expected!</div>")
                    .contains("<div style='white-space:pre-wrap;'>java.lang.IllegalStateException");
            });
    }

    private String getResponseBody(WebTestClient client) {
        return client.get()
            .uri("/")
            .accept(MediaType.TEXT_HTML)
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
            .expectHeader()
            .contentType(TEXT_HTML_UTF8)
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();
    }
}
