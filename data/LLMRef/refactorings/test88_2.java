public class test88 {

    @Test
    	void alwaysIncludeMessage() {
    		this.contextRunner
    			.withPropertyValues("server.error.include-exception=true", "server.error.include-message=always")
    			.run(this::verifyErrorMessageForInternalServerError);
    	}
    
    private void verifyErrorMessageForInternalServerError(WebTestClient client) {
        client.get()
            .uri("/?trace=false")
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
            .jsonPath("message")
            .isNotEmpty()
            .jsonPath("requestId")
            .isEqualTo(this.logIdFilter.getLogId());
    }
}
