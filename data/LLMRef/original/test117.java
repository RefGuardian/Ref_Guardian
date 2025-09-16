public class test117 {

    @Test
    	@WithResource(name = "ksLocP")
    	@WithResource(name = "tsLocP")
    	void producerProperties() {
    		this.contextRunner.withPropertyValues("spring.kafka.clientId=cid",
    				"spring.kafka.properties.foo.bar.baz=qux.fiz.buz", "spring.kafka.producer.acks=all",
    				"spring.kafka.producer.batch-size=2KB", "spring.kafka.producer.bootstrap-servers=bar:1234", // test
    				// override
    				"spring.kafka.producer.buffer-memory=4KB", "spring.kafka.producer.compression-type=gzip",
    				"spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.LongSerializer",
    				"spring.kafka.producer.retries=2", "spring.kafka.producer.properties.fiz.buz=fix.fox",
    				"spring.kafka.producer.security.protocol=SSL", "spring.kafka.producer.ssl.key-password=p4",
    				"spring.kafka.producer.ssl.key-store-location=classpath:ksLocP",
    				"spring.kafka.producer.ssl.key-store-password=p5", "spring.kafka.producer.ssl.key-store-type=PKCS12",
    				"spring.kafka.producer.ssl.trust-store-location=classpath:tsLocP",
    				"spring.kafka.producer.ssl.trust-store-password=p6",
    				"spring.kafka.producer.ssl.trust-store-type=PKCS12", "spring.kafka.producer.ssl.protocol=TLSv1.2",
    				"spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.IntegerSerializer")
    			.run((context) -> {
    				DefaultKafkaProducerFactory<?, ?> producerFactory = context.getBean(DefaultKafkaProducerFactory.class);
    				Map<String, Object> configs = producerFactory.getConfigurationProperties();
    				// common
    				assertThat(configs).containsEntry(ProducerConfig.CLIENT_ID_CONFIG, "cid");
    				// producer
    				assertThat(configs).containsEntry(ProducerConfig.ACKS_CONFIG, "all");
    				assertThat(configs).containsEntry(ProducerConfig.BATCH_SIZE_CONFIG, 2048);
    				assertThat(configs).containsEntry(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
    						Collections.singletonList("bar:1234")); // override
    				assertThat(configs).containsEntry(ProducerConfig.BUFFER_MEMORY_CONFIG, 4096L);
    				assertThat(configs).containsEntry(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
    				assertThat(configs).containsEntry(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
    				assertThat(configs).containsEntry(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
    				assertThat(configs).containsEntry(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "p4");
    				assertThat((String) configs.get(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG))
    					.endsWith(File.separator + "ksLocP");
    				assertThat(configs).containsEntry(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "p5");
    				assertThat(configs).containsEntry(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "PKCS12");
    				assertThat((String) configs.get(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG))
    					.endsWith(File.separator + "tsLocP");
    				assertThat(configs).containsEntry(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "p6");
    				assertThat(configs).containsEntry(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "PKCS12");
    				assertThat(configs).containsEntry(SslConfigs.SSL_PROTOCOL_CONFIG, "TLSv1.2");
    				assertThat(configs).containsEntry(ProducerConfig.RETRIES_CONFIG, 2);
    				assertThat(configs).containsEntry(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
    						IntegerSerializer.class);
    				assertThat(context.getBeansOfType(KafkaJaasLoginModuleInitializer.class)).isEmpty();
    				assertThat(context.getBeansOfType(KafkaTransactionManager.class)).isEmpty();
    				assertThat(configs).containsEntry("foo.bar.baz", "qux.fiz.buz");
    				assertThat(configs).containsEntry("fiz.buz", "fix.fox");
    			});
    	}
}
