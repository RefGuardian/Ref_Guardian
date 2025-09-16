public class test122 {

    private static final String BOOTSTRAP_SERVER = "kafka.example.com:12345";
    private static final String SPRING_KAFKA_STREAMS_APPLICATION_ID = "test";
    
    @Test
    	void connectionDetailsWithSslBundleAreAppliedToStreams() {
    		SslBundle sslBundle = SslBundle.of(SslStoreBundle.NONE);
    		KafkaConnectionDetails connectionDetails = new KafkaConnectionDetails() {
    			@Override
    			public List<String> getBootstrapServers() {
    				return List.of(BOOTSTRAP_SERVER);
    			}
    
    			@Override
    			public Configuration getStreams() {
    				return Configuration.of(getBootstrapServers(), sslBundle);
    			}
    		};
    		this.contextRunner.withUserConfiguration(EnableKafkaStreamsConfiguration.class)
    			.withPropertyValues("spring.kafka.streams.auto-startup=false", "spring.kafka.streams.application-id=" + SPRING_KAFKA_STREAMS_APPLICATION_ID)
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
}
