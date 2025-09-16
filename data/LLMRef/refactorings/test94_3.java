public class test94 {

    @Test
    void shouldSupplyRestClientBuilderConfigurerWithCustomSettings() {
        ClientHttpRequestFactorySettings clientHttpRequestFactorySettings = ClientHttpRequestFactorySettings.defaults()
            .withRedirects(Redirects.DONT_FOLLOW);
        ClientHttpRequestFactoryBuilder<?> clientHttpRequestFactoryBuilder = mock(
                ClientHttpRequestFactoryBuilder.class);
        RestClientCustomizer customizer1 = mock(RestClientCustomizer.class);
        RestClientCustomizer customizer2 = mock(RestClientCustomizer.class);
        HttpMessageConvertersRestClientCustomizer httpMessageConverterCustomizer = mock(
                HttpMessageConvertersRestClientCustomizer.class);
        this.contextRunner.withBean(ClientHttpRequestFactorySettings.class, () -> clientHttpRequestFactorySettings)
            .withBean(ClientHttpRequestFactoryBuilder.class, () -> clientHttpRequestFactoryBuilder)
            .withBean("customizer1", RestClientCustomizer.class, () -> customizer1)
            .withBean("customizer2", RestClientCustomizer.class, () -> customizer2)
            .withBean("httpMessageConverterCustomizer", HttpMessageConvertersRestClientCustomizer.class,
                () -> httpMessageConverterCustomizer)
            .run((context) -> {
                assertThat(context).hasSingleBean(RestClientBuilderConfigurer.class)
                    .hasSingleBean(ClientHttpRequestFactorySettings.class)
                    .hasSingleBean(ClientHttpRequestFactoryBuilder.class);
                RestClientBuilderConfigurer configurer = context.getBean(RestClientBuilderConfigurer.class);
                assertThat(configurer).hasFieldOrPropertyWithValue("requestFactoryBuilder",
                        clientHttpRequestFactoryBuilder);
                assertThat(configurer).hasFieldOrPropertyWithValue("requestFactorySettings",
                        clientHttpRequestFactorySettings);
                assertThat(configurer).hasFieldOrPropertyWithValue("customizers",
                        List.of(customizer1, customizer2, httpMessageConverterCustomizer));
            });
    }
}
