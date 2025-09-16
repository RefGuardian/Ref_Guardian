public class test84 {

    @Test
    	void includeStackTraceOnParam() {
    		runContext((context) -> {
    			performWebTest(context);
    		});
    	}

    	private void runContext(Consumer<WebTestClient> consumer) {
    		this.contextRunner
    			.withPropertyValues("server.error.include-exception=true", "server.error.include-stacktrace=on-param")
    			.run((context) -> {
    				WebTestClient client = getWebClient(context);
    				consumer.accept(client);
    			});
    	}

    	private void performWebTest(WebTestClient client) {
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
    	}
}
