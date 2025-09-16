public class test143 {

    private static final String SPRING_PULSAR_CONSUMER_NAME = "spring.pulsar.consumer.name";
    private static final String SPRING_PULSAR_CONSUMER_SUBSCRIPTION_INITIAL_POSITION = "spring.pulsar.consumer.subscription.initial-position";
    private static final String SPRING_PULSAR_CONSUMER_SUBSCRIPTION_MODE = "spring.pulsar.consumer.subscription.mode";
    private static final String SPRING_PULSAR_CONSUMER_SUBSCRIPTION_NAME = "spring.pulsar.consumer.subscription.name";
    private static final String SPRING_PULSAR_CONSUMER_SUBSCRIPTION_TOPICS_MODE = "spring.pulsar.consumer.subscription.topics-mode";
    private static final String SPRING_PULSAR_CONSUMER_SUBSCRIPTION_TYPE = "spring.pulsar.consumer.subscription.type";
    private static final String SPRING_PULSAR_CONSUMER_TOPICS_0 = "spring.pulsar.consumer.topics[0]";
    private static final String SPRING_PULSAR_CONSUMER_TOPICS_PATTERN = "spring.pulsar.consumer.topics-pattern";
    private static final String SPRING_PULSAR_CONSUMER_PRIORITY_LEVEL = "spring.pulsar.consumer.priority-level";
    private static final String SPRING_PULSAR_CONSUMER_READ_COMPACTED = "spring.pulsar.consumer.read-compacted";
    private static final String SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_MAX_REDELIVER_COUNT = "spring.pulsar.consumer.dead-letter-policy.max-redeliver-count";
    private static final String SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_RETRY_LETTER_TOPIC = "spring.pulsar.consumer.dead-letter-policy.retry-letter-topic";
    private static final String SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_DEAD_LETTER_TOPIC = "spring.pulsar.consumer.dead-letter-policy.dead-letter-topic";
    private static final String SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_INITIAL_SUBSCRIPTION_NAME = "spring.pulsar.consumer.dead-letter-policy.initial-subscription-name";
    private static final String SPRING_PULSAR_CONSUMER_RETRY_ENABLE = "spring.pulsar.consumer.retry-enable";

    @Test
    void bind() {
        Map<String, String> map = new HashMap<>();
        map.put(SPRING_PULSAR_CONSUMER_NAME, "my-consumer");
        map.put(SPRING_PULSAR_CONSUMER_SUBSCRIPTION_INITIAL_POSITION, "earliest");
        map.put(SPRING_PULSAR_CONSUMER_SUBSCRIPTION_MODE, "nondurable");
        map.put(SPRING_PULSAR_CONSUMER_SUBSCRIPTION_NAME, "my-subscription");
        map.put(SPRING_PULSAR_CONSUMER_SUBSCRIPTION_TOPICS_MODE, "all-topics");
        map.put(SPRING_PULSAR_CONSUMER_SUBSCRIPTION_TYPE, "shared");
        map.put(SPRING_PULSAR_CONSUMER_TOPICS_0, "my-topic");
        map.put(SPRING_PULSAR_CONSUMER_TOPICS_PATTERN, "my-pattern");
        map.put(SPRING_PULSAR_CONSUMER_PRIORITY_LEVEL, "8");
        map.put(SPRING_PULSAR_CONSUMER_READ_COMPACTED, "true");
        map.put(SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_MAX_REDELIVER_COUNT, "4");
        map.put(SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_RETRY_LETTER_TOPIC, "my-retry-topic");
        map.put(SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_DEAD_LETTER_TOPIC, "my-dlt-topic");
        map.put(SPRING_PULSAR_CONSUMER_DEAD_LETTER_POLICY_INITIAL_SUBSCRIPTION_NAME, "my-initial-subscription");
        map.put(SPRING_PULSAR_CONSUMER_RETRY_ENABLE, "true");
        PulsarProperties.Consumer properties = bindProperties(map).getConsumer();
        assertThat(properties.getName()).isEqualTo("my-consumer");
        assertThat(properties.getSubscription()).satisfies((subscription) -> {
            assertThat(subscription.getName()).isEqualTo("my-subscription");
            assertThat(subscription.getType()).isEqualTo(SubscriptionType.Shared);
            assertThat(subscription.getMode()).isEqualTo(SubscriptionMode.NonDurable);
            assertThat(subscription.getInitialPosition()).isEqualTo(SubscriptionInitialPosition.Earliest);
            assertThat(subscription.getTopicsMode()).isEqualTo(RegexSubscriptionMode.AllTopics);
        });
        assertThat(properties.getTopics()).containsExactly("my-topic");
        assertThat(properties.getTopicsPattern().toString()).isEqualTo("my-pattern");
        assertThat(properties.getPriorityLevel()).isEqualTo(8);
        assertThat(properties.isReadCompacted()).isTrue();
        assertThat(properties.getDeadLetterPolicy()).satisfies((policy) -> {
            assertThat(policy.getMaxRedeliverCount()).isEqualTo(4);
            assertThat(policy.getRetryLetterTopic()).isEqualTo("my-retry-topic");
            assertThat(policy.getDeadLetterTopic()).isEqualTo("my-dlt-topic");
            assertThat(policy.getInitialSubscriptionName()).isEqualTo("my-initial-subscription");
        });
        assertThat(properties.isRetryEnable()).isTrue();
    }
}
