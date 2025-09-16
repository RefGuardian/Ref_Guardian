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
			.run(this::validateContext);
	}

	private void validateContext(ConfigurableApplicationContext context) {
		assertThat(context).hasSingleBean(ReactiveJwtDecoder.class);
		ReactiveJwtDecoder reactiveJwtDecoder = context.getBean(ReactiveJwtDecoder.class);
		validate(
				jwt().claim("iss", URI.create(this.issuerUri).toURL())
					.claim("aud", List.of("https://test-audience.com")),
				reactiveJwtDecoder,
				(validators) -> assertThat(validators).hasAtLeastOneElementOfType(JwtIssuerValidator.class)
					.satisfiesOnlyOnce(audClaimValidator()));
	}
}
