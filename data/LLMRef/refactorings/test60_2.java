public class test60 {

    private static final String DEFAULT_ISSUER_URI = "http://localhost:8080";

    @Test
    	void autoConfigurationShouldConfigureResourceServerUsingOAuthIssuerUri() throws Exception {
    		this.server = new MockWebServer();
    		this.server.start();
    		String issuer = this.server.url("").toString();
    		String cleanIssuerPath = cleanIssuerPath(issuer);
    		setupMockResponsesWithErrors(cleanIssuerPath, 2);
    		this.contextRunner
    			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=" + DEFAULT_ISSUER_URI)
    			.run((context) -> {
    				assertThat(context).hasSingleBean(SupplierReactiveJwtDecoder.class);
    				assertFilterConfiguredWithJwtAuthenticationManager(context);
    				assertThat(context.containsBean("jwtDecoderByIssuerUri")).isTrue();
    				// Trigger calls to the issuer by decoding a token
    				decodeJwt(context);
    				assertJwkSetUriReactiveJwtDecoderBuilderCustomization(context);
    			});
    		// The last request is to the JWK Set endpoint to look up the algorithm
    		assertThat(this.server.getRequestCount()).isEqualTo(4);
    	}
}
