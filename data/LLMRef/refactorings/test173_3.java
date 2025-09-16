public class test173 {

    @Test
    @WithPackageResources("test.jks")
    @SuppressWarnings("unchecked")
    void configureWithSslBundle() {
        List<String> properties = createPropertiesList();
        this.contextRunner.withPropertyValues(properties.toArray(String[]::new)).run((context) -> {
            validateRestClient(context);
        });
    }

    private List<String> createPropertiesList() {
        List<String> properties = new ArrayList<>();
        properties.add("spring.elasticsearch.restclient.ssl.bundle=mybundle");
        properties.add("spring.ssl.bundle.jks.mybundle.truststore.location=classpath:test.jks");
        properties.add("spring.ssl.bundle.jks.mybundle.options.ciphers=DESede");
        properties.add("spring.ssl.bundle.jks.mybundle.options.enabled-protocols=TLSv1.3");
        return properties;
    }

    private void validateRestClient(ConfigurableApplicationContext context) {
        assertThat(context).hasSingleBean(RestClient.class);
        RestClient restClient = context.getBean(RestClient.class);
        validateStrategy(restClient);
    }

    private void validateStrategy(RestClient restClient) {
        Object client = ReflectionTestUtils.getField(restClient, "client");
        Object connmgr = ReflectionTestUtils.getField(client, "connmgr");
        Registry<SchemeIOSessionStrategy> registry = (Registry<SchemeIOSessionStrategy>) ReflectionTestUtils
            .getField(connmgr, "ioSessionFactoryRegistry");
        SchemeIOSessionStrategy strategy = registry.lookup("https");
        validateAssertion(strategy);
    }

    private void validateAssertion(SchemeIOSessionStrategy strategy) {
        assertThat(strategy).extracting("sslContext").isNotNull();
        assertThat(strategy).extracting("supportedCipherSuites")
            .asInstanceOf(InstanceOfAssertFactories.ARRAY)
            .containsExactly("DESede");
        assertThat(strategy).extracting("supportedProtocols")
            .asInstanceOf(InstanceOfAssertFactories.ARRAY)
            .containsExactly("TLSv1.3");
    }
}
