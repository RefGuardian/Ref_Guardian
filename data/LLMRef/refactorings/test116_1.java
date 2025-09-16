public class test116 {

    @Test
    void connectionDetailsWithSslBundleAreAppliedToConsumer() {
        SslBundle sslBundle = SslBundle.of(SslStoreBundle.NONE);
        KafkaConnectionDetails connectionDetails = createKafkaConnectionDetails(sslBundle);
        this.contextRunner.withBean(KafkaConnectionDetails.class, () -> connectionDetails).run((context) -> {
            assertThat(context).hasSingleBean(KafkaConnectionDetails.class);
            DefaultKafkaConsumerFactory<?, ?> consumerFactory = context.getBean(DefaultKafkaConsumerFactory.class);
            Map<String, Object> configs = consumerFactory.getConfigurationProperties();
            assertThat(configs).containsEntry("ssl.engine.factory.class", SslBundleSslEngineFactory.class);
            assertThat(configs).containsEntry("org.springframework.boot.ssl.SslBundle", sslBundle);
        });
    }

    private KafkaConnectionDetails createKafkaConnectionDetails(SslBundle sslBundle) {
        return new KafkaConnectionDetails() {
            @Override
            public List<String> getBootstrapServers() {
                return List.of("kafka.example.com:12345");
            }

            @Override
            public Configuration getConsumer() {
                return Configuration.of(getBootstrapServers(), sslBundle);
            }

        };
    }
}
