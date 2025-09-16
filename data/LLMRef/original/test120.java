public class test120 {

    @Test
    	void connectionDetailsWithSslBundleAreAppliedToAdmin() {
    		SslBundle sslBundle = SslBundle.of(SslStoreBundle.NONE);
    		KafkaConnectionDetails connectionDetails = new KafkaConnectionDetails() {
    			@Override
    			public List<String> getBootstrapServers() {
    				return List.of("kafka.example.com:12345");
    			}
    
    			@Override
    			public Configuration getAdmin() {
    				return Configuration.of(getBootstrapServers(), sslBundle);
    			}
    
    		};
    		this.contextRunner.withBean(KafkaConnectionDetails.class, () -> connectionDetails).run((context) -> {
    			assertThat(context).hasSingleBean(KafkaConnectionDetails.class);
    			KafkaAdmin admin = context.getBean(KafkaAdmin.class);
    			Map<String, Object> configs = admin.getConfigurationProperties();
    			assertThat(configs).containsEntry("ssl.engine.factory.class", SslBundleSslEngineFactory.class);
    			assertThat(configs).containsEntry("org.springframework.boot.ssl.SslBundle", sslBundle);
    		});
    	}
}
