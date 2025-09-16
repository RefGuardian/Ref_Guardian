public class test83 {

    @Test
    void bindingResultErrorIncludeMessageAndErrors() {
        this.contextRunner.withPropertyValues("server.error.include-message=on-param", "server.error.include-binding-errors=on-param").run((context) -> {
            WebTestClient client = getWebClient(context);
            client.post().uri("/bind?message=true&errors=true").contentType(MediaType.APPLICATION_JSON).bodyValue("{}").exchange().expectStatus().isBadRequest().expectBody().jsonPath("status").isEqualTo("400").jsonPath("error").isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase()).jsonPath("path").isEqualTo(("/bind")).jsonPath("exception").doesNotExist().jsonPath("errors").isArray().jsonPath("message").isNotEmpty().jsonPath("requestId").isEqualTo(this.logIdFilter.getLogId());
        });
    }
}
