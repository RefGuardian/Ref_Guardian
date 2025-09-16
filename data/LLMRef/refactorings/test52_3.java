public class test52 {

    private static final String PATH = "test";

    @SuppressWarnings("unchecked")
    @Test
    void autoConfigurationShouldConfigureResourceServerUsingOAuthIssuerUri() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String issuer = this.server.url(PATH).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponsesWithErrors(cleanIssuerPath, 2);
    
        this.contextRunner
            .withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
                    + this.server.getHostName() + ":" + this.server.getPort() + "/" + PATH)
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
        assertThat(this.server.getRequestCount()).isEqualTo(4);
    }
}
