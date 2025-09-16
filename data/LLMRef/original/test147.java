public class test147 {

    @Test
    	@SuppressWarnings("unchecked")
    	void customizeMessageSenderBuilder() {
    		PulsarProperties properties = new PulsarProperties();
    		properties.getProducer().setName("name");
    		properties.getProducer().setTopicName("topicname");
    		properties.getProducer().setSendTimeout(Duration.ofSeconds(1));
    		properties.getProducer().setMessageRoutingMode(MessageRoutingMode.RoundRobinPartition);
    		properties.getProducer().setHashingScheme(HashingScheme.JavaStringHash);
    		properties.getProducer().setBatchingEnabled(false);
    		properties.getProducer().setChunkingEnabled(true);
    		properties.getProducer().setCompressionType(CompressionType.SNAPPY);
    		properties.getProducer().setAccessMode(ProducerAccessMode.Exclusive);
    		ReactiveMessageSenderBuilder<Object> builder = mock(ReactiveMessageSenderBuilder.class);
    		new PulsarReactivePropertiesMapper(properties).customizeMessageSenderBuilder(builder);
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
