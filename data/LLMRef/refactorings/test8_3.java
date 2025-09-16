public class test8 {

    @Test
    	void userDefinedMappingsSecureByDefault() {
    		WebClient client = createWebClient();
    		
    		client.get().header("Authorization", getBasicAuth()).exchangeToMono(this::handleResponse)
    			.flatMap(this::handleSessionResponse)
    			.delayElement(Duration.ofSeconds(10))
    			.flatMap(sessionCookie -> handleUnauthorizedRequest(client, sessionCookie))
    			.block(Duration.ofSeconds(30));
    	}
    
    private WebClient createWebClient() {
        return this.webClientBuilder.baseUrl("http://localhost:" + this.port + "/").build();
    }

    private Mono<Tuple2<String, String>> handleResponse(ClientResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        return response.bodyToMono(String.class)
            .map(sessionId -> Tuples.of(response.cookies().getFirst("SESSION").getValue(), sessionId));
    }

    private Mono<String> handleSessionResponse(Tuple2<String, String> tuple) {
        String sessionCookie = tuple.getT1();
        WebClient client = createWebClient();
        
        return client.get().cookie("SESSION", sessionCookie).exchangeToMono(response -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
            return response.bodyToMono(String.class)
                    .doOnNext(sessionId -> assertThat(sessionId).isEqualTo(tuple.getT2()))
                    .thenReturn(sessionCookie);
        });
    }

    private Mono<Void> handleUnauthorizedRequest(WebClient client, String sessionCookie) {
        return client.get().cookie("SESSION", sessionCookie).exchangeToMono(response -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
            return response.releaseBody();
        });
    }
}
