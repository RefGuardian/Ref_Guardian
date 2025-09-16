public class test54 {

    private static final String CONSTANT_TEST = "test";

    @SuppressWarnings("unchecked")
    	@Test
    	void autoConfigurationShouldConfigureAudienceValidatorIfPropertyProvidedAndIssuerUri() throws Exception {
    		this.server = new MockWebServer();
    		this.server.start();
    		String path = CONSTANT_TEST;
    		String issuer = this.server.url(path).toString();
    		String cleanIssuerPath = cleanIssuerPath(issuer);
    		setupMockResponse(cleanIssuerPath);
    		String issuerUri = "http://" + this.server.getHostName() + ":" + this.server.getPort() + "/" + path;
    		this.contextRunner.withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=" + issuerUri,
    				"spring.security.oauth2.resourceserver.jwt.audiences=https://test-audience.com,https://test-audience1.com")
    			.run((context) -> {
    				SupplierJwtDecoder supplierJwtDecoderBean = context.getBean(SupplierJwtDecoder.class);
    				Supplier<JwtDecoder> jwtDecoderSupplier = (Supplier<JwtDecoder>) ReflectionTestUtils
    					.getField(supplierJwtDecoderBean, "delegate");
    				JwtDecoder jwtDecoder = jwtDecoderSupplier.get();
    				validate(
    						jwt().claim("iss", URI.create(issuerUri).toURL())
    							.claim("aud", List.of("https://test-audience.com")),
    						jwtDecoder,
    						(validators) -> assertThat(validators).hasAtLeastOneElementOfType(JwtIssuerValidator.class)
    							.satisfiesOnlyOnce(audClaimValidator()));
    			});
    	}
}
