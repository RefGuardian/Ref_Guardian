public class test122 {

    @Test
    	void connectionDetailsWithSslBundleAreAppliedToStreams() {
    		SslBundle sslBundle = SslBundle.of(SslStoreBundle.NONE);
    		KafkaConnectionDetails connectionDetails = createKafkaConnectionDetails(sslBundle);
    		this.contextRunner.withUserConfiguration(EnableKafkaStreamsConfiguration.class)
    			.withPropertyValues("spring.kafka.streams.auto-startup=false", "spring.kafka.streams.application-id=test")
    			.withBean(KafkaConnectionDetails.class, () -> connectionDetails)
    			.run((context) -> {
    				assertThat(context).hasSingleBean(KafkaConnectionDetails.class);
    				Properties configs = context
    					.getBean(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME,
    							KafkaStreamsConfiguration.class)
    					.asProperties();
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
            public Configuration getStreams() {
                return Configuration.of(getBootstrapServers(), sslBundle);
            }
        };
    }
}
