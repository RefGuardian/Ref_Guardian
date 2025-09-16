public class test118 {

    @Test
    	void connectionDetailsWithSslBundleAreAppliedToProducer() {
    		SslBundle sslBundle = SslBundle.of(SslStoreBundle.NONE);
    		KafkaConnectionDetails connectionDetails = new KafkaConnectionDetails() {
    			@Override
    			public List<String> getBootstrapServers() {
    				return List.of("kafka.example.com:12345");
    			}
    
    			@Override
    			public Configuration getProducer() {
    				return Configuration.of(getBootstrapServers(), sslBundle);
    			}
    
    		};
    		this.contextRunner.withBean(KafkaConnectionDetails.class, () -> connectionDetails).run((context) -> {
    			assertThat(context).hasSingleBean(KafkaConnectionDetails.class);
    			DefaultKafkaProducerFactory<?, ?> producerFactory = context.getBean(DefaultKafkaProducerFactory.class);
    			Map<String, Object> configs = producerFactory.getConfigurationProperties();
    			assertThat(configs).containsEntry("ssl.engine.factory.class", SslBundleSslEngineFactory.class);
    			assertThat(configs).containsEntry("org.springframework.boot.ssl.SslBundle", sslBundle);
    		});
    	}
}
