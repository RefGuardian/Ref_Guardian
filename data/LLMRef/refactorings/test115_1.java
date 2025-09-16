public class test115 {

    private static final String SPRING_KAFKA_BOOTSTRAP_SERVERS = "spring.kafka.bootstrap-servers:";
    private static final String SPRING_KAFKA_CONSUMER_GROUP_ID = "spring.kafka.consumer.group-id=testGroup";
    private static final String SPRING_KAFKA_RETRY_TOPIC_ENABLED = "spring.kafka.retry.topic.enabled=true";
    private static final String SPRING_KAFKA_RETRY_TOPIC_ATTEMPTS = "spring.kafka.retry.topic.attempts=5";
    private static final String SPRING_KAFKA_RETRY_TOPIC_DELAY = "spring.kafka.retry.topic.delay=100ms";
    private static final String SPRING_KAFKA_RETRY_TOPIC_MULTIPLIER = "spring.kafka.retry.topic.multiplier=2";
    private static final String SPRING_KAFKA_RETRY_TOPIC_MAX_DELAY = "spring.kafka.retry.topic.max-delay=300ms";
    private static final String SPRING_KAFKA_CONSUMER_AUTO_OFFSET_RESET = "spring.kafka.consumer.auto-offset-reset=earliest";

    @SuppressWarnings("unchecked")
    @Test
    void testEndToEndWithRetryTopics() throws Exception {
        load(KafkaConfig.class, SPRING_KAFKA_BOOTSTRAP_SERVERS + getEmbeddedKafkaBrokersAsString(),
                SPRING_KAFKA_CONSUMER_GROUP_ID, SPRING_KAFKA_RETRY_TOPIC_ENABLED,
                SPRING_KAFKA_RETRY_TOPIC_ATTEMPTS, SPRING_KAFKA_RETRY_TOPIC_DELAY,
                SPRING_KAFKA_RETRY_TOPIC_MULTIPLIER, SPRING_KAFKA_RETRY_TOPIC_MAX_DELAY,
                SPRING_KAFKA_CONSUMER_AUTO_OFFSET_RESET);
        RetryTopicConfiguration configuration = this.context.getBean(RetryTopicConfiguration.class);
        assertThat(configuration.getDestinationTopicProperties()).extracting(DestinationTopic.Properties::delay)
                .containsExactly(0L, 100L, 200L, 300L, 0L);
        KafkaTemplate<String, String> template = this.context.getBean(KafkaTemplate.class);
        template.send(TEST_RETRY_TOPIC, "foo", "bar");
        RetryListener listener = this.context.getBean(RetryListener.class);
        assertThat(listener.latch.await(30, TimeUnit.SECONDS)).isTrue();
        assertThat(listener).extracting(RetryListener::getKey, RetryListener::getReceived)
                .containsExactly("foo", "bar");
        assertThat(listener).extracting(RetryListener::getTopics)
                .asInstanceOf(InstanceOfAssertFactories.LIST)
                .hasSize(5)
                .containsSequence("testRetryTopic", "testRetryTopic-retry-0", "testRetryTopic-retry-1",
                        "testRetryTopic-retry-2");
    }
}
