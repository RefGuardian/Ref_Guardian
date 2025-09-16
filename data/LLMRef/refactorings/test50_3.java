public class test50 {

    private static final String DEFAULT_PATH = "test";

    @SuppressWarnings("unchecked")
    @Test
    void autoConfigurationShouldConfigureResourceServerUsingOidcIssuerUri() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String path = DEFAULT_PATH;
        String issuer = this.server.url(path).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponse(cleanIssuerPath);
        this.contextRunner
                .withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
                        + this.server.getHostName() + ":" + this.server.getPort() + "/" + path)
                .run((context) -> {
                    assertThat(context).hasSingleBean(SupplierJwtDecoder.class);
                    assertThat(context.containsBean("jwtDecoderByIssuerUri")).isTrue();
                    SupplierJwtDecoder supplierJwtDecoderBean = context.getBean(SupplierJwtDecoder.class);
                    Supplier<JwtDecoder> jwtDecoderSupplier = (Supplier<JwtDecoder>) ReflectionTestUtils
                            .getField(supplierJwtDecoderBean, "delegate");
                    jwtDecoderSupplier.get();
                    assertJwkSetUriJwtDecoderBuilderCustomization(context);
                });
        // The last request is to the JWK Set endpoint to look up the algorithm
        assertThat(this.server.getRequestCount()).isEqualTo(2);
    }
}
