public class test55 {

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
    		this.contextRunner.withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=" + issuerUri)
    			.withUserConfiguration(CustomJwtClaimValidatorConfig.class)
    			.run((context) -> {
    				SupplierJwtDecoder supplierJwtDecoderBean = context.getBean(SupplierJwtDecoder.class);
    				Supplier<JwtDecoder> jwtDecoderSupplier = (Supplier<JwtDecoder>) ReflectionTestUtils
    					.getField(supplierJwtDecoderBean, "delegate");
    				JwtDecoder jwtDecoder = jwtDecoderSupplier.get();
    				assertThat(context).hasBean("customJwtClaimValidator");
    				OAuth2TokenValidator<Jwt> customValidator = (OAuth2TokenValidator<Jwt>) context
    					.getBean("customJwtClaimValidator");
    				validate(jwt().claim("iss", URI.create(issuerUri).toURL()).claim("custom_claim", "custom_claim_value"),
    						jwtDecoder, (validators) -> assertThat(validators).contains(customValidator)
    							.hasAtLeastOneElementOfType(JwtIssuerValidator.class));
    			});
    	}
}
