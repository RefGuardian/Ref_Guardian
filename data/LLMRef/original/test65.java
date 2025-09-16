public class test65 {

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
    			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://jwk-set-uri.com",
    					"spring.security.oauth2.resourceserver.jwt.issuer-uri=" + issuerUri)
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
