public class test81 {

    @Test
    void notFound() {
        this.contextRunner.run(this::verifyNotFound);
    }

    private void verifyNotFound(WebTestClient client) {
        client.get()
            .uri("/notFound")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody()
            .jsonPath("status")
            .isEqualTo("404")
            .jsonPath("error")
            .isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase())
            .jsonPath("path")
            .isEqualTo(("/notFound"))
            .jsonPath("exception")
            .doesNotExist()
            .jsonPath("requestId")
            .isEqualTo(this.logIdFilter.getLogId());
    }
}
