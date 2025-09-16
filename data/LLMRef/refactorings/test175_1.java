public class test175 {

    private static final String PATH_PREFIX = "/test";
    private static final int MAX_CONN_TOTAL = 100;
    private static final String COOKIE_SPEC = "rfc6265-lax";

    @Bean
    RestClientBuilderCustomizer myCustomizer() {
        return new RestClientBuilderCustomizer() {

            @Override
            public void customize(RestClientBuilder builder) {
                builder.setPathPrefix(PATH_PREFIX);
            }

            @Override
            public void customize(HttpAsyncClientBuilder builder) {
                builder.setMaxConnTotal(MAX_CONN_TOTAL);
            }

            @Override
            public void customize(RequestConfig.Builder builder) {
                builder.setCookieSpec(COOKIE_SPEC);
            }

        };
    }
}
