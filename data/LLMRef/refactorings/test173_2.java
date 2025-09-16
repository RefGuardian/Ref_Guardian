public class test173 {

    @Test
    @WithPackageResources("test.jks")
    @SuppressWarnings("unchecked")
    void configureWithSslBundle() {
        List<String> properties = initProperties();
        this.contextRunner.withPropertyValues(properties.toArray(String[]::new)).run((context) -> {
            assertThat(context).hasSingleBean(RestClient.class);
            RestClient restClient = context.getBean(RestClient.class);
            Object client = ReflectionTestUtils.getField(restClient, "client");
            Object connmgr = ReflectionTestUtils.getField(client, "connmgr");
            Registry<SchemeIOSessionStrategy> registry = getRegistry(connmgr);
            SchemeIOSessionStrategy strategy = registry.lookup("https");
            assertStrategy(strategy);
        });
    }

    private List<String> initProperties() {
        List<String> properties = new ArrayList<>();
        properties.add("spring.elasticsearch.restclient.ssl.bundle=mybundle");
        properties.add("spring.ssl.bundle.jks.mybundle.truststore.location=classpath:test.jks");
        properties.add("spring.ssl.bundle.jks.mybundle.options.ciphers=DESede");
        properties.add("spring.ssl.bundle.jks.mybundle.options.enabled-protocols=TLSv1.3");
        return properties;
    }

    private Registry<SchemeIOSessionStrategy> getRegistry(Object connmgr) {
        return (Registry<SchemeIOSessionStrategy>) ReflectionTestUtils.getField(connmgr, "ioSessionFactoryRegistry");
    }

    private void assertStrategy(SchemeIOSessionStrategy strategy) {
        assertThat(strategy).extracting("sslContext").isNotNull();
        assertThat(strategy).extracting("supportedCipherSuites")
            .asInstanceOf(InstanceOfAssertFactories.ARRAY)
            .containsExactly("DESede");
        assertThat(strategy).extracting("supportedProtocols")
            .asInstanceOf(InstanceOfAssertFactories.ARRAY)
            .containsExactly("TLSv1.3");
    }
}
