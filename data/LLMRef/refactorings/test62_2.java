public class test62 {

    private static final String ISSUER_PROPERTY = "spring.security.oauth2.resourceserver.jwt.issuer-uri";
    private static final String AUDIENCES_PROPERTY = "spring.security.oauth2.resourceserver.jwt.audiences";
    
    @SuppressWarnings("unchecked")
    @Test
    void autoConfigurationShouldConfigureAudienceValidatorIfPropertyProvidedAndIssuerUri() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String path = "test";
        String issuer = this.server.url(path).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponse(cleanIssuerPath);
        String issuerUri = "http://" + this.server.getHostName() + ":" + this.server.getPort() + "/" + path;
        this.contextRunner.withPropertyValues(ISSUER_PROPERTY + "=" + issuerUri,
                AUDIENCES_PROPERTY + "=https://test-audience.com,https://test-audience1.com")
                .run((context) -> {
                    SupplierReactiveJwtDecoder supplierJwtDecoderBean = context.getBean(SupplierReactiveJwtDecoder.class);
                    Mono<ReactiveJwtDecoder> jwtDecoderSupplier = (Mono<ReactiveJwtDecoder>) ReflectionTestUtils
                            .getField(supplierJwtDecoderBean, "jwtDecoderMono");
                    ReactiveJwtDecoder jwtDecoder = jwtDecoderSupplier.block();
                    validate(
                            jwt().claim("iss", URI.create(issuerUri).toURL())
                                    .claim("aud", List.of("https://test-audience.com")),
                            jwtDecoder,
                            (validators) -> assertThat(validators).hasAtLeastOneElementOfType(JwtIssuerValidator.class)
                                    .satisfiesOnlyOnce(audClaimValidator()));
                });
    }
}
