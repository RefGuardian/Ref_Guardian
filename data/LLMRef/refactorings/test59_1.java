public class test59 {

    @Test
    	void autoConfigurationShouldConfigureResourceServerUsingOidcRfc8414IssuerUri() throws Exception {
    		this.server = new MockWebServer();
    		this.server.start();
    		String issuer = this.server.url("").toString();
    		String cleanIssuerPath = cleanIssuerPath(issuer);
    		setupMockResponsesWithErrors(cleanIssuerPath, 1);
    		this.contextRunner
    			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
    					+ this.server.getHostName() + ":" + this.server.getPort())
    			.run((context) -> {
    				assertThat(context).hasSingleBean(SupplierReactiveJwtDecoder.class);
    				assertFilterConfiguredWithJwtAuthenticationManager(context);
    				assertThat(context.containsBean("jwtDecoderByIssuerUri")).isTrue();
    				// Trigger calls to the issuer by decoding a token
    				decodeJwt(context);
    				// assertJwkSetUriReactiveJwtDecoderBuilderCustomization(context);
    			});
    		// The last request is to the JWK Set endpoint to look up the algorithm
    		assertThat(this.server.getRequestCount()).isEqualTo(3);
    	}

	private String cleanIssuerPath(String issuer) {
		// Extracted Method
		return issuer.replace("http://", "").replace("https://", "").replace("/", "");
	}
}
