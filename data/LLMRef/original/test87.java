public class test87 {

    @Test
    	void includeMessageOnParam() {
    		this.contextRunner
    			.withPropertyValues("server.error.include-exception=true", "server.error.include-message=on-param")
    			.run((context) -> {
    				WebTestClient client = getWebClient(context);
    				client.get()
    					.uri("/?message=true")
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
    			});
    	}
}
