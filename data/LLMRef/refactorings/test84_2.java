public class test84 {

    @Test
    void includeStackTraceOnParam() {
        this.runContext((context) -> {
            WebTestClient client = getWebClient(context);
            client.get()
                .uri("/?trace=true")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody()
                .jsonPath("status")
                .isEqualTo("500")
                .jsonPath("error")
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .jsonPath("exception")
                .isEqualTo(IllegalStateException.class.getName())
                .jsonPath("trace")
                .exists()
                .jsonPath("requestId")
                .isEqualTo(this.logIdFilter.getLogId());
        });
    }

    private void runContext(Consumer<Context> function) {
        this.contextRunner
            .withPropertyValues("server.error.include-exception=true", "server.error.include-stacktrace=on-param")
            .run(function);
    }
}
