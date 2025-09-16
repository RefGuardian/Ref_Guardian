public class test122 {

    private static final String SSL_ENGINE_FACTORY_CLASS = "ssl.engine.factory.class";
    private static final String SSL_BUNDLE_BEAN_NAME = "org.springframework.boot.ssl.SslBundle";

    @Test
    void connectionDetailsWithSslBundleAreAppliedToStreams() {
        SslBundle sslBundle = SslBundle.of(SslStoreBundle.NONE);
        KafkaConnectionDetails connectionDetails = new KafkaConnectionDetails() {
            @Override
            public List<String> getBootstrapServers() {
                return List.of("kafka.example.com:12345");
            }

            @Override
            public Configuration getStreams() {
                return Configuration.of(getBootstrapServers(), sslBundle);
            }
        };
        this.contextRunner.withUserConfiguration(EnableKafkaStreamsConfiguration.class)
            .withPropertyValues("spring.kafka.streams.auto-startup=false", "spring.kafka.streams.application-id=test")
            .withBean(KafkaConnectionDetails.class, () -> connectionDetails)
            .run((context) -> {
                assertThat(context).hasSingleBean(KafkaConnectionDetails.class);
                Properties configs = context
                    .getBean(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME,
                            KafkaStreamsConfiguration.class)
                    .asProperties();
                assertThat(configs).containsEntry(SSL_ENGINE_FACTORY_CLASS, SslBundleSslEngineFactory.class);
                assertThat(configs).containsEntry(SSL_BUNDLE_BEAN_NAME, sslBundle);
            });
    }
}
