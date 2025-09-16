public class test51 {

    private static final int EXPECTED_REQUEST_COUNT = 3;

    @SuppressWarnings("unchecked")
    	@Test
    	void autoConfigurationShouldConfigureResourceServerUsingOidcRfc8414IssuerUri() throws Exception {
    		this.server = new MockWebServer();
    		this.server.start();
    		String path = "test";
    		String issuer = this.server.url(path).toString();
    		String cleanIssuerPath = cleanIssuerPath(issuer);
    		setupMockResponsesWithErrors(cleanIssuerPath, 1);
    		this.contextRunner
    			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
    					+ this.server.getHostName() + ":" + this.server.getPort() + "/" + path)
    			.run((context) -> {
    				assertThat(context).hasSingleBean(SupplierJwtDecoder.class);
    				assertThat(context.containsBean("jwtDecoderByIssuerUri")).isTrue();
    				SupplierJwtDecoder supplierJwtDecoderBean = context.getBean(SupplierJwtDecoder.class);
    				Supplier<JwtDecoder> jwtDecoderSupplier = (Supplier<JwtDecoder>) ReflectionTestUtils
    					.getField(supplierJwtDecoderBean, "delegate");
    				jwtDecoderSupplier.get();
    				assertJwkSetUriJwtDecoderBuilderCustomization(context);
    			});
    		assertThat(this.server.getRequestCount()).isEqualTo(EXPECTED_REQUEST_COUNT);
    	}
}
