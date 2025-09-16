public class test8 {

    @Test
    void userDefinedMappingsSecureByDefault() {
        WebClient client = this.webClientBuilder.baseUrl("http://localhost:" + this.port + "/").build();
        String basicAuth = getBasicAuth();
        client.get().header("Authorization", basicAuth).exchangeToMono((response) -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
            return response.bodyToMono(String.class)
                .map((sessionId) -> Tuples.of(response.cookies().getFirst("SESSION").getValue(), sessionId));
        }).flatMap((tuple) -> {
            String sessionCookie = tuple.getT1();
            return client.get().cookie("SESSION", sessionCookie).exchangeToMono((response) -> {
                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
                return response.bodyToMono(String.class)
                    .doOnNext((sessionId) -> assertThat(sessionId).isEqualTo(tuple.getT2()))
                    .thenReturn(sessionCookie);
            });
        })
            .delayElement(Duration.ofSeconds(10))
            .flatMap((sessionCookie) -> client.get().cookie("SESSION", sessionCookie).exchangeToMono((response) -> {
                assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
                return response.releaseBody();
            }))
            .block(Duration.ofSeconds(30));
    }
}
