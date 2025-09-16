public class test121 {

    @Test
    	@SuppressWarnings("unchecked")
    	@WithResource(name = "ksLocP")
    	@WithResource(name = "tsLocP")
    	void streamsProperties() {
    		this.contextRunner.withUserConfiguration(EnableKafkaStreamsConfiguration.class)
    			.withPropertyValues("spring.kafka.client-id=cid",
    					"spring.kafka.bootstrap-servers=localhost:9092,localhost:9093", "spring.application.name=appName",
    					"spring.kafka.properties.foo.bar.baz=qux.fiz.buz", "spring.kafka.streams.auto-startup=false",
    					"spring.kafka.streams.state-store-cache-max-size=1KB", "spring.kafka.streams.client-id=override",
    					"spring.kafka.streams.properties.fiz.buz=fix.fox", "spring.kafka.streams.replication-factor=2",
    					"spring.kafka.streams.state-dir=/tmp/state", "spring.kafka.streams.security.protocol=SSL",
    					"spring.kafka.streams.ssl.key-password=p7",
    					"spring.kafka.streams.ssl.key-store-location=classpath:ksLocP",
    					"spring.kafka.streams.ssl.key-store-password=p8", "spring.kafka.streams.ssl.key-store-type=PKCS12",
    					"spring.kafka.streams.ssl.trust-store-location=classpath:tsLocP",
    					"spring.kafka.streams.ssl.trust-store-password=p9",
    					"spring.kafka.streams.ssl.trust-store-type=PKCS12", "spring.kafka.streams.ssl.protocol=TLSv1.2")
    			.run((context) -> {
    				Properties configs = context
    					.getBean(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME,
    							KafkaStreamsConfiguration.class)
    					.asProperties();
    				assertThat((List<String>) configs.get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG))
    					.containsExactly("localhost:9092", "localhost:9093");
    				assertThat(configs).containsEntry(StreamsConfig.STATESTORE_CACHE_MAX_BYTES_CONFIG, 1024);
    				assertThat(configs).containsEntry(StreamsConfig.CLIENT_ID_CONFIG, "override");
    				assertThat(configs).containsEntry(StreamsConfig.REPLICATION_FACTOR_CONFIG, 2);
    				assertThat(configs).containsEntry(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
    				assertThat(configs).containsEntry(StreamsConfig.STATE_DIR_CONFIG, "/tmp/state");
    				assertThat(configs).containsEntry(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "p7");
    				assertThat((String) configs.get(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG))
    					.endsWith(File.separator + "ksLocP");
    				assertThat(configs).containsEntry(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "p8");
    				assertThat(configs).containsEntry(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "PKCS12");
    				assertThat((String) configs.get(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG))
    					.endsWith(File.separator + "tsLocP");
    				assertThat(configs).containsEntry(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "p9");
    				assertThat(configs).containsEntry(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "PKCS12");
    				assertThat(configs).containsEntry(SslConfigs.SSL_PROTOCOL_CONFIG, "TLSv1.2");
    				assertThat(context.getBeansOfType(KafkaJaasLoginModuleInitializer.class)).isEmpty();
    				assertThat(configs).containsEntry("foo.bar.baz", "qux.fiz.buz");
    				assertThat(configs).containsEntry("fiz.buz", "fix.fox");
    				assertThat(context.getBean(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME))
    					.isNotNull();
    			});
    	}
}
