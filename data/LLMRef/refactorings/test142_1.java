public class test142 {

    @Test
    void bind() {
        Map<String, String> map = new HashMap<>();
        map.put("spring.pulsar.producer.name", "my-producer");
        map.put("spring.pulsar.producer.topic-name", "my-topic");
        map.put("spring.pulsar.producer.send-timeout", "2s");
        map.put("spring.pulsar.producer.message-routing-mode", "custompartition");
        map.put("spring.pulsar.producer.hashing-scheme", "murmur3_32hash");
        map.put("spring.pulsar.producer.batching-enabled", "false");
        map.put("spring.pulsar.producer.chunking-enabled", "true");
        map.put("spring.pulsar.producer.compression-type", "lz4");
        map.put("spring.pulsar.producer.access-mode", "exclusive");
        map.put("spring.pulsar.producer.cache.expire-after-access", "2s");
        map.put("spring.pulsar.producer.cache.maximum-size", "3");
        map.put("spring.pulsar.producer.cache.initial-capacity", "5");
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

    private static PulsarProperties bindProperties(Map<String, String> map) {
        return null; // Dummy return, replace with actual logic
    }
}
