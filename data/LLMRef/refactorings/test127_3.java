public class test127 {

    @Test
    void SseSubscriptionShouldWork() {
        testWithWebClient((client) -> {
            String query = "{ booksOnSale(minPages: 50){ id name pageCount author } }";
            EntityExchangeResult<String> result = client.post()
                    .uri("/graphql")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .bodyValue("{  \"query\": \"subscription TestSubscription " + query + "\"}")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                    .expectBody(String.class)
                    .returnResult();
            assertThat(result.getResponseBody()).contains("event:next",
                    "data:{\"data\":{\"booksOnSale\":{\"id\":\"book-1\",\"name\":\"GraphQL for beginners\",\"pageCount\":100,\"author\":\"John GraphQL\"}}}",
                    "event:next",
                    "data:{\"data\":{\"booksOnSale\":{\"id\":\"book-2\",\"name\":\"Harry Potter and the Philosopher's Stone\",\"pageCount\":223,\"author\":\"Joanne Rowling\"}}}",
                    "event:complete");
        });
    }
}
