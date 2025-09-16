public class test53 {

    @Test
    	void autoConfigurationShouldConfigureAudienceAndIssuerJwtValidatorIfPropertyProvided() throws Exception {
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
    				assertThat(context).hasSingleBean(JwtDecoder.class);
    				JwtDecoder jwtDecoder = context.getBean(JwtDecoder.class);
    				validate(
    						jwt().claim("iss", URI.create(issuerUri).toURL())
    							.claim("aud", List.of("https://test-audience.com")),
    						jwtDecoder,
    						(validators) -> assertThat(validators).hasAtLeastOneElementOfType(JwtIssuerValidator.class)
    							.satisfiesOnlyOnce(audClaimValidator()));
    			});
    	}
}
