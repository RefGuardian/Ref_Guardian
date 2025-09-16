public class test58 {

    private static final String TEST_PATH = "test";

    @Test
    void autoConfigurationShouldConfigureResourceServerUsingOidcIssuerUri() throws IOException {
        this.server = new MockWebServer();
        this.server.start();
        String path = TEST_PATH;
        String issuer = this.server.url(path).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponse(cleanIssuerPath);

        this.contextRunner
            .withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
                    + this.server.getHostName() + ":" + this.server.getPort() + "/" + path)
            .run((context) -> {
                assertThat(context).hasSingleBean(SupplierReactiveJwtDecoder.class);
                assertFilterConfiguredWithJwtAuthenticationManager(context);
                assertThat(context.containsBean("jwtDecoderByIssuerUri")).isTrue();
                // Trigger calls to the issuer by decoding a token
                decodeJwt(context);
                assertJwkSetUriReactiveJwtDecoderBuilderCustomization(context);
            });

        // The last request is to the JWK Set endpoint to look up the algorithm
        assertThat(this.server.getRequestCount()).isEqualTo(2);
    }
}
