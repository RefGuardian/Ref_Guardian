public class test147 {

    @Test
	@SuppressWarnings("unchecked")
	void customizeMessageSenderBuilder() {
		PulsarProperties properties = new PulsarProperties();
		ReactiveMessageSenderBuilder<Object> builder = mock(ReactiveMessageSenderBuilder.class);
		populateProducerProperties(properties.getProducer());
		new PulsarReactivePropertiesMapper(properties).customizeMessageSenderBuilder(builder);
		verifyBuilderAssertions(builder);
	}

	private void populateProducerProperties(ProducerProperties producer) {
		producer.setName("name");
		producer.setTopicName("topicname");
		producer.setSendTimeout(Duration.ofSeconds(1));
		producer.setMessageRoutingMode(MessageRoutingMode.RoundRobinPartition);
		producer.setHashingScheme(HashingScheme.JavaStringHash);
		producer.setBatchingEnabled(false);
		producer.setChunkingEnabled(true);
		producer.setCompressionType(CompressionType.SNAPPY);
		producer.setAccessMode(ProducerAccessMode.Exclusive);
	}

	private void verifyBuilderAssertions(ReactiveMessageSenderBuilder<Object> builder) {
		then(builder).should().producerName("name");
		then(builder).should().topic("topicname");
		then(builder).should().sendTimeout(Duration.ofSeconds(1));
		then(builder).should().messageRoutingMode(MessageRoutingMode.RoundRobinPartition);
		then(builder).should().hashingScheme(HashingScheme.JavaStringHash);
		then(builder).should().batchingEnabled(false);
		then(builder).should().chunkingEnabled(true);
		then(builder).should().compressionType(CompressionType.SNAPPY);
		then(builder).should().accessMode(ProducerAccessMode.Exclusive);
	}
}
