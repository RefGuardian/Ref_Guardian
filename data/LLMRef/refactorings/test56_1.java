public class test56 {

    private static final String JWK_SET_URI = "https://jwk-set-uri.com";
    private static final List<String> AUDIENCES = Arrays.asList("https://test-audience.com", "https://test-audience1.com");

    @SuppressWarnings("unchecked")
    @Test
    void audienceValidatorWhenAudienceInvalid() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String path = "test";
        String issuer = this.server.url(path).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponse(cleanIssuerPath);
        String issuerUri = "http://" + this.server.getHostName() + ":" + this.server.getPort() + "/" + path;
        this.contextRunner.withPropertyValues(
            "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=" + JWK_SET_URI,
            "spring.security.oauth2.resourceserver.jwt.issuer-uri=" + issuerUri,
            "spring.security.oauth2.resourceserver.jwt.audiences=" + String.join(",", AUDIENCES))
            .run((context) -> {
                assertThat(context).hasSingleBean(JwtDecoder.class);
                JwtDecoder jwtDecoder = context.getBean(JwtDecoder.class);
                DelegatingOAuth2TokenValidator<Jwt> jwtValidator = (DelegatingOAuth2TokenValidator<Jwt>) ReflectionTestUtils
                    .getField(jwtDecoder, "jwtValidator");
                Jwt jwt = jwt().claim("iss", new URL(issuerUri))
                    .claim("aud", Collections.singletonList("https://other-audience.com"))
                    .build();
                assertThat(jwtValidator.validate(jwt).hasErrors()).isTrue();
            });
    }
}
