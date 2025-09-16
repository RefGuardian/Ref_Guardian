public class test51 {

    private static final String TEST_PATH = "test";

    @SuppressWarnings("unchecked")
    @Test
    void autoConfigurationShouldConfigureResourceServerUsingOidcRfc8414IssuerUri() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String issuer = this.server.url(TEST_PATH).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponsesWithErrors(cleanIssuerPath, 1);
        this.contextRunner
            .withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
                    + this.server.getHostName() + ":" + this.server.getPort() + "/" + TEST_PATH)
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
        assertThat(this.server.getRequestCount()).isEqualTo(3);
    }
}
