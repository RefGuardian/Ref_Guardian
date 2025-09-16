public class test93 {

    private static final Redirects DEFAULT_REDIRECTS = Redirects.DONT_FOLLOW;
    private static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofHours(1);
    private static final Duration DEFAULT_READ_TIMEOUT = Duration.ofDays(1);

    @Test
    void shouldSupplyRestClientSslIfSslBundlesIsThereWithCustomHttpSettingsAndBuilder() {
        SslBundles sslBundles = mock(SslBundles.class);
        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.defaults()
                .withRedirects(DEFAULT_REDIRECTS)
                .withConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .withReadTimeout(DEFAULT_READ_TIMEOUT)
                .withSslBundle(mock(SslBundle.class));
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
}
