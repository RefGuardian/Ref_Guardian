public class test147 {

    @Test
    @SuppressWarnings("unchecked")
    void customizeMessageSenderBuilder() {
        PulsarProperties properties = new PulsarProperties();
        customizeProducer(properties.getProducer());
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

    private void customizeProducer(ProducerProperties producer) {
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
}
