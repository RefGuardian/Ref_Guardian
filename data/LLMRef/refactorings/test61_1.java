public class test61 {

    @Test
    void autoConfigurationShouldConfigureIssuerAndAudienceJwtValidatorIfPropertyProvided() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
        String path = "test";
        String issuer = this.server.url(path).toString();
        String cleanIssuerPath = cleanIssuerPath(issuer);
        setupMockResponse(cleanIssuerPath);
        String issuerUri = "http://" + this.server.getHostName() + ":" + this.server.getPort() + "/" + path;
        this.contextRunner.withPropertyValues(
                "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://jwk-set-uri.com",
                "spring.security.oauth2.resourceserver.jwt.issuer-uri=" + issuerUri,
                "spring.security.oauth2.resourceserver.jwt.audiences=https://test-audience.com,https://test-audience1.com")
            .run((context) -> {
                assertThat(context).hasSingleBean(ReactiveJwtDecoder.class);
                ReactiveJwtDecoder reactiveJwtDecoder = context.getBean(ReactiveJwtDecoder.class);
                validateJWTClaim(
                        jwt().claim("iss", URI.create(issuerUri).toURL())
                            .claim("aud", List.of("https://test-audience.com")),
                        reactiveJwtDecoder,
                        (validators) -> assertThat(validators).hasAtLeastOneElementOfType(JwtIssuerValidator.class)
                            .satisfiesOnlyOnce(audClaimValidator()));
            });
    }

    private void validateJWTClaim(Object obj1, Object obj2, Object obj3) {
    	validate(obj1, obj2, obj3);
	}
}
