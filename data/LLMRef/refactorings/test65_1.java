public class test65 {

    private static final String JWK_SET_URI = "https://jwk-set-uri.com";
    private static final String SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI = "spring.security.oauth2.resourceserver.jwt.issuer-uri=";
    
    @SuppressWarnings("unchecked")
    @Test
    void customValidatorWhenInvalid() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String path = "test";
        String issuer = this.server.url(path).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponse(cleanIssuerPath);
        String issuerUri = "http://" + this.server.getHostName() + ":" + this.server.getPort() + "/" + path;
        this.contextRunner
            .withPropertyValues(JWK_SET_URI,
                    SPRING_SECURITY_OAUTH2_RESOURCE_SERVER_JWT_ISSUER_URI + issuerUri)
            .withUserConfiguration(CustomJwtClaimValidatorConfig.class)
            .run((context) -> {
                assertThat(context).hasSingleBean(ReactiveJwtDecoder.class);
                ReactiveJwtDecoder jwtDecoder = context.getBean(ReactiveJwtDecoder.class);
                DelegatingOAuth2TokenValidator<Jwt> jwtValidator = (DelegatingOAuth2TokenValidator<Jwt>) ReflectionTestUtils
                    .getField(jwtDecoder, "jwtValidator");
                Jwt jwt = jwt().claim("iss", new URL(issuerUri)).claim("custom_claim", "invalid_value").build();
                assertThat(jwtValidator.validate(jwt).hasErrors()).isTrue();
            });
    }
}
