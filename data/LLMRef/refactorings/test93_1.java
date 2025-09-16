public class test93 {

    @Test
    	void shouldSupplyRestClientSslIfSslBundlesIsThereWithCustomHttpSettingsAndBuilder() {
    		SslBundles sslBundles = mock(SslBundles.class);
    	 ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = getHttpRequestFactorySettings();
    	 ClientHttpRequestFactoryBuilder<?> clientHttpRequestFactoryBuilder = mock(
    				ClientHttpRequestFactoryBuilder.class);
    	 this.contextRunner.withBean(SslBundles.class, () -> sslBundles)
    			.withBean(ClientHttpRequestFactorySettings.class, () -> clientHttpRequestFactorySettings)
    			.withBean(ClientHttpRequestFactoryBuilder.class, () -> clientHttpRequestFactoryBuilder)
    			.run((context) -> {
    				assertThat(context).hasSingleBean(RestClientSsl.class);
    				RestClientSsl restClientSsl = context.getBean(RestClientSsl.class);
    				assertThat(restClientSsl).hasFieldOrPropertyWithValue("sslBundles", sslBundles);
    				assertThat(restClientSsl).hasFieldOrPropertyWithValue("builder", clientHttpRequestFactoryBuilder);
    				assertThat(restClientSsl).hasFieldOrPropertyWithValue("settings", clientHttpRequestFactorySettings);
    			});
    	}

    private ClientHttpRequestFactorySettings getHttpRequestFactorySettings() {
        return ClientHttpRequestFactorySettings.defaults()
            .withRedirects(Redirects.DONT_FOLLOW)
            .withConnectTimeout(Duration.ofHours(1))
            .withReadTimeout(Duration.ofDays(1))
            .withSslBundle(mock(SslBundle.class));
    }
}
