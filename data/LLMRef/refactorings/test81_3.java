public class test81 {

    @Test
    	void notFound() {
    		assertResponseForPath("/notFound", HttpStatus.NOT_FOUND.getReasonPhrase(), "404", this.logIdFilter.getLogId());
    	}

    	private void assertResponseForPath(String path, String error, String status, String logId) {
    		this.contextRunner.run((context) -> {
    			WebTestClient client = getWebClient(context);
    			client.get()
    				.uri(path)
    				.exchange()
    				.expectStatus()
    				.isNotFound()
    				.expectBody()
    				.jsonPath("status")
    				.isEqualTo(status)
    				.jsonPath("error")
    				.isEqualTo(error)
    				.jsonPath("path")
    				.isEqualTo(path)
    				.jsonPath("exception")
    				.doesNotExist()
    				.jsonPath("requestId")
    				.isEqualTo(logId);
    		});
    	}
}
