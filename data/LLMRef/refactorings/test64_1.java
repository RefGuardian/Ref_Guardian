public class test64 {

    private static final String JWK_SET_URI = "https://jwk-set-uri.com";
    private static final String ISSUER_URI = "http://issuer.com/test";

    @SuppressWarnings("unchecked")
    @Test
    void audienceValidatorWhenAudienceInvalid() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String path = "test";
        String issuer = this.server.url(path).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponse(cleanIssuerPath);
        String issuerUri = ISSUER_URI;
        this.contextRunner.withPropertyValues(
            "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=" + JWK_SET_URI,
            "spring.security.oauth2.resourceserver.jwt.issuer-uri=" + issuerUri,
            "spring.security.oauth2.resourceserver.jwt.audiences=https://test-audience.com,https://test-audience1.com"
        ).run((context) -> {
            assertThat(context).hasSingleBean(ReactiveJwtDecoder.class);
            ReactiveJwtDecoder jwtDecoder = context.getBean(ReactiveJwtDecoder.class);
            DelegatingOAuth2TokenValidator<Jwt> jwtValidator = (DelegatingOAuth2TokenValidator<Jwt>) ReflectionTestUtils
                .getField(jwtDecoder, "jwtValidator");
            Jwt jwt = jwt().claim("iss", new URL(issuerUri))
                .claim("aud", Collections.singletonList("https://other-audience.com"))
                .build();
            assertThat(jwtValidator.validate(jwt).hasErrors()).isTrue();
        });
    }
}
