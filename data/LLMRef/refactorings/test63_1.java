public class test63 {

    private static final String JWK_SET_URI = "https://jwk-set-uri.com";
    private static final String SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI = "spring.security.oauth2.resourceserver.jwt.issuer-uri";

    @SuppressWarnings("unchecked")
    	@Test
    	void autoConfigurationShouldConfigureCustomValidators() throws Exception {
    		this.server = new MockWebServer();
    		this.server.start();
    		String path = "test";
    		String issuer = this.server.url(path).toString();
    		String cleanIssuerPath = cleanIssuerPath(issuer);
    		setupMockResponse(cleanIssuerPath);
    		String issuerUri = "http://" + this.server.getHostName() + ":" + this.server.getPort() + "/" + path;
    		this.contextRunner
    			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.jwk-set-uri=" + JWK_SET_URI, 
    					SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI + "=" + issuerUri)
    			.withUserConfiguration(CustomJwtClaimValidatorConfig.class)
    			.run((context) -> {
    				assertThat(context).hasSingleBean(ReactiveJwtDecoder.class);
    				ReactiveJwtDecoder reactiveJwtDecoder = context.getBean(ReactiveJwtDecoder.class);
    				OAuth2TokenValidator<Jwt> customValidator = (OAuth2TokenValidator<Jwt>) context
    					.getBean("customJwtClaimValidator");
    				validate(jwt().claim("iss", URI.create(issuerUri).toURL()).claim("custom_claim", "custom_claim_value"),
    						reactiveJwtDecoder, (validators) -> assertThat(validators).contains(customValidator)
    							.hasAtLeastOneElementOfType(JwtIssuerValidator.class));
    			});
    	}
}
