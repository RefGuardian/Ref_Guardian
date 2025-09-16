public class test60 {

    @Test
	void autoConfigurationShouldConfigureResourceServerUsingOAuthIssuerUri() throws Exception {
		this.server = new MockWebServer();
		this.server.start();
		String issuer = this.server.url("").toString();
		String cleanIssuerPath = cleanIssuerPath(issuer);
		setupMockResponsesWithErrors(cleanIssuerPath, 2);
		this.contextRunner
			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.issuer-uri=http://"
					+ this.server.getHostName() + ":" + this.server.getPort())
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

	private String cleanIssuerPath(String issuer) {
		return UrlUtils.addTrailingSlash(issuer);
	}
}
