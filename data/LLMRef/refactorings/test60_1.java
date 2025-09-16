public class test60 {

    @Test
    	void autoConfigurationShouldConfigureResourceServerUsingOAuthIssuerUri() throws Exception {
    		this.server = new MockWebServer();
    		this.server.start();
    		String issuer = this.server.url("").toString();
    		String cleanIssuerPath = cleanIssuerPath(issuer);
    		setupMockResponsesWithErrors(cleanIssuerPath, 2);
    		setupContextRunner(this.server);
    		assertThat(this.server.getRequestCount()).isEqualTo(4);
    	}

	private void setupContextRunner(MockWebServer server) {
		this.contextRunner
			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
					+ server.getHostName() + ":" + server.getPort())
			.run((context) -> {
				assertThat(context).hasSingleBean(SupplierReactiveJwtDecoder.class);
				assertFilterConfiguredWithJwtAuthenticationManager(context);
				assertThat(context.containsBean("jwtDecoderByIssuerUri")).isTrue();
				// Trigger calls to the issuer by decoding a token
				decodeJwt(context);
				assertJwkSetUriReactiveJwtDecoderBuilderCustomization(context);
			});
	}
}
