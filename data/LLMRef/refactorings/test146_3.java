public class test146 {

    private static final String TOPIC = "mytopic";
    private static final String TOPIC_PATTERN = "my-pattern";
    private static final String DEAD_LETTER_TOPIC = "my-dlt";
    private static final int MAX_REDELIVER_COUNT = 1;
    private static final String CONSUMER_NAME = "name";
    private static final int PRIORITY_LEVEL = 123;
    private static final boolean READ_COMPACTED = true;

    @Test
    @SuppressWarnings("unchecked")
    void customizeConsumerBuilder() {
        PulsarProperties properties = new PulsarProperties();
        List<String> topics = List.of(TOPIC);
        Pattern topicsPattern = Pattern.compile(TOPIC_PATTERN);
        properties.getConsumer().setName(CONSUMER_NAME);
        properties.getConsumer().setTopics(topics);
        properties.getConsumer().setTopicsPattern(topicsPattern);
        properties.getConsumer().setPriorityLevel(PRIORITY_LEVEL);
        properties.getConsumer().setReadCompacted(READ_COMPACTED);
        Consumer.DeadLetterPolicy deadLetterPolicy = new Consumer.DeadLetterPolicy();
        deadLetterPolicy.setDeadLetterTopic(DEAD_LETTER_TOPIC);
        deadLetterPolicy.setMaxRedeliverCount(MAX_REDELIVER_COUNT);
        properties.getConsumer().setDeadLetterPolicy(deadLetterPolicy);
        ConsumerBuilder<Object> builder = mock(ConsumerBuilder.class);
        new PulsarPropertiesMapper(properties).customizeConsumerBuilder(builder);
        then(builder).should().consumerName(CONSUMER_NAME);
        then(builder).should().topics(topics);
        then(builder).should().topicsPattern(topicsPattern);
        then(builder).should().priorityLevel(PRIORITY_LEVEL);
        then(builder).should().readCompacted(READ_COMPACTED);
        then(builder).should().deadLetterPolicy(new DeadLetterPolicy(MAX_REDELIVER_COUNT, null, DEAD_LETTER_TOPIC, null, null, null));
    }
}
