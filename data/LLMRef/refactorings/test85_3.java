public class test85 {

    @Test
    	void alwaysIncludeStackTrace() {
    		test85.testMethod(contextRunner);
    	}

    private static void testMethod(ContextRunner contextRunner) {
        contextRunner
            .withPropertyValues("server.error.include-exception=true", "server.error.include-stacktrace=always")
            .run((context) -> {
                WebTestClient client = getWebClient(context);
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
                    .jsonPath("trace")
                    .exists()
                    .jsonPath("requestId")
                    .isEqualTo(this.logIdFilter.getLogId());
            });
    }

}
