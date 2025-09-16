public class test142 {

    private static final String SPRING_PULSAR_PRODUCER_NAME = "spring.pulsar.producer.name";
    private static final String SPRING_PULSAR_PRODUCER_TOPIC_NAME = "spring.pulsar.producer.topic-name";
    private static final String SPRING_PULSAR_PRODUCER_SEND_TIMEOUT = "spring.pulsar.producer.send-timeout";
    private static final String SPRING_PULSAR_PRODUCER_MESSAGE_ROUTING_MODE = "spring.pulsar.producer.message-routing-mode";
    private static final String SPRING_PULSAR_PRODUCER_HASHING_SCHEME = "spring.pulsar.producer.hashing-scheme";
    private static final String SPRING_PULSAR_PRODUCER_BATCHING_ENABLED = "spring.pulsar.producer.batching-enabled";
    private static final String SPRING_PULSAR_PRODUCER_CHUNKING_ENABLED = "spring.pulsar.producer.chunking-enabled";
    private static final String SPRING_PULSAR_PRODUCER_COMPRESSION_TYPE = "spring.pulsar.producer.compression-type";
    private static final String SPRING_PULSAR_PRODUCER_ACCESS_MODE = "spring.pulsar.producer.access-mode";
    private static final String SPRING_PULSAR_PRODUCER_CACHE_EXPIRE_AFTER_ACCESS = "spring.pulsar.producer.cache.expire-after-access";
    private static final String SPRING_PULSAR_PRODUCER_CACHE_MAXIMUM_SIZE = "spring.pulsar.producer.cache.maximum-size";
    private static final String SPRING_PULSAR_PRODUCER_CACHE_INITIAL_CAPACITY = "spring.pulsar.producer.cache.initial-capacity";

    @Test
    void bind() {
        Map<String, String> map = new HashMap<>();
        map.put(SPRING_PULSAR_PRODUCER_NAME, "my-producer");
        map.put(SPRING_PULSAR_PRODUCER_TOPIC_NAME, "my-topic");
        map.put(SPRING_PULSAR_PRODUCER_SEND_TIMEOUT, "2s");
        map.put(SPRING_PULSAR_PRODUCER_MESSAGE_ROUTING_MODE, "custompartition");
        map.put(SPRING_PULSAR_PRODUCER_HASHING_SCHEME, "murmur3_32hash");
        map.put(SPRING_PULSAR_PRODUCER_BATCHING_ENABLED, "false");
        map.put(SPRING_PULSAR_PRODUCER_CHUNKING_ENABLED, "true");
        map.put(SPRING_PULSAR_PRODUCER_COMPRESSION_TYPE, "lz4");
        map.put(SPRING_PULSAR_PRODUCER_ACCESS_MODE, "exclusive");
        map.put(SPRING_PULSAR_PRODUCER_CACHE_EXPIRE_AFTER_ACCESS, "2s");
        map.put(SPRING_PULSAR_PRODUCER_CACHE_MAXIMUM_SIZE, "3");
        map.put(SPRING_PULSAR_PRODUCER_CACHE_INITIAL_CAPACITY, "5");
        PulsarProperties.Producer properties = bindProperties(map).getProducer();
        assertThat(properties.getName()).isEqualTo("my-producer");
        assertThat(properties.getTopicName()).isEqualTo("my-topic");
        assertThat(properties.getSendTimeout()).isEqualTo(Duration.ofSeconds(2));
        assertThat(properties.getMessageRoutingMode()).isEqualTo(MessageRoutingMode.CustomPartition);
        assertThat(properties.getHashingScheme()).isEqualTo(HashingScheme.Murmur3_32Hash);
        assertThat(properties.isBatchingEnabled()).isFalse();
        assertThat(properties.isChunkingEnabled()).isTrue();
        assertThat(properties.getCompressionType()).isEqualTo(CompressionType.LZ4);
        assertThat(properties.getAccessMode()).isEqualTo(ProducerAccessMode.Exclusive);
        assertThat(properties.getCache().getExpireAfterAccess()).isEqualTo(Duration.ofSeconds(2));
        assertThat(properties.getCache().getMaximumSize()).isEqualTo(3);
        assertThat(properties.getCache().getInitialCapacity()).isEqualTo(5);
    }
}
