public class test82 {

    @Test
    void bindingResultError() {
        this.contextRunner.run((context) -> {
            WebTestClient client = getWebClient(context);
            postRequest(client);
        });
    }

    private void postRequest(WebTestClient client) {
        client.post()
            .uri("/bind")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{}")
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody()
            .jsonPath("status")
            .isEqualTo("400")
            .jsonPath("error")
            .isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .jsonPath("path")
            .isEqualTo(("/bind"))
            .jsonPath("exception")
            .doesNotExist()
            .jsonPath("errors")
            .doesNotExist()
            .jsonPath("message")
            .doesNotExist()
            .jsonPath("requestId")
            .isEqualTo(this.logIdFilter.getLogId());
    }
}
