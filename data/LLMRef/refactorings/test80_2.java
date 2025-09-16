public class test80 {

    @Test
    void jsonError(CapturedOutput output) {
        this.contextRunner.run((context) -> {
            WebTestClient client = getWebClient(context);
            test80.verifyResponse(client, output);
        });
    }

    private static void verifyResponse(WebTestClient client, CapturedOutput output) {
        client.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody()
                .jsonPath("status")
                .isEqualTo("500")
                .jsonPath("error")
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .jsonPath("path")
                .isEqualTo(("/"))
                .jsonPath("message")
                .doesNotExist()
                .jsonPath("exception")
                .doesNotExist()
                .jsonPath("trace")
                .doesNotExist()
                .jsonPath("requestId")
                .isEqualTo(this.logIdFilter.getLogId());
        assertThat(output).contains("500 Server Error for HTTP GET \"/\"")
                .contains("java.lang.IllegalStateException: Expected!");
    }
}
