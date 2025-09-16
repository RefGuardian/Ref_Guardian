public class test145 {

    @Test
    @SuppressWarnings("unchecked")
    void customizeProducerBuilder() {
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
        ProducerBuilder<Object> builder = new ProducerBuilder<>(properties);
        then(builder).should().producerName("name");
        then(builder).should().topic("topicname");
        then(builder).should().sendTimeout(1000, TimeUnit.MILLISECONDS);
        then(builder).should().messageRoutingMode(MessageRoutingMode.RoundRobinPartition);
        then(builder).should().hashingScheme(HashingScheme.JavaStringHash);
        then(builder).should().enableBatching(false);
        then(builder).should().enableChunking(true);
        then(builder).should().compressionType(CompressionType.SNAPPY);
        then(builder).should().accessMode(ProducerAccessMode.Exclusive);
    }
}
