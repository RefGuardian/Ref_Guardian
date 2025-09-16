public class test175 {

    @Bean
    RestClientBuilderCustomizer myCustomizer() {
        return createCustomizer();
    }

    private RestClientBuilderCustomizer createCustomizer() {
        return new RestClientBuilderCustomizer() {

            @Override
            public void customize(RestClientBuilder builder) {
                builder.setPathPrefix("/test");
            }

            @Override
            public void customize(HttpAsyncClientBuilder builder) {
                builder.setMaxConnTotal(100);
            }

            @Override
            public void customize(RequestConfig.Builder builder) {
                builder.setCookieSpec("rfc6265-lax");
            }

        };
    }
}
