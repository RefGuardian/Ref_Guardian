public class test2 {

    private static final String BASE_URI = "http://localhost:";

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        RestTemplate rest = createRestTemplate();
        rest.setErrorHandler(new NoOpResponseErrorHandler());
        rest.setUriTemplateHandler(new UriTemplateHandler() {

            @Override
            public URI expand(String uriTemplate, Object... uriVariables) {
                return URI.create(BASE_URI + RestTemplateParameterResolver.this.launcher.getHttpPort() + uriTemplate);
            }

            @Override
            public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
                return URI.create(BASE_URI + RestTemplateParameterResolver.this.launcher.getHttpPort() + uriTemplate);
            }

        });
        return rest;
    }

    private RestTemplate createRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.custom()
                .setRetryStrategy(new DefaultHttpRequestRetryStrategy(10, TimeValue.of(1, TimeUnit.SECONDS))
                .build()));
    }
}
