public class test146 {

    @Test
    	@SuppressWarnings("unchecked")
    	void customizeConsumerBuilder() {
    		PulsarProperties properties = new PulsarProperties();
    		List<String> topics = List.of("mytopic");
    		Pattern topisPattern = Pattern.compile("my-pattern");
    		properties.getConsumer().setName("name");
    		properties.getConsumer().setTopics(topics);
    		properties.getConsumer().setTopicsPattern(topisPattern);
    		properties.getConsumer().setPriorityLevel(123);
    		properties.getConsumer().setReadCompacted(true);
    		Consumer.DeadLetterPolicy deadLetterPolicy = new Consumer.DeadLetterPolicy();
    		deadLetterPolicy.setDeadLetterTopic("my-dlt");
    		deadLetterPolicy.setMaxRedeliverCount(1);
    		properties.getConsumer().setDeadLetterPolicy(deadLetterPolicy);
    		ConsumerBuilder<Object> builder = mock(ConsumerBuilder.class);
    		new PulsarPropertiesMapper(properties).customizeConsumerBuilder(builder);
    		then(builder).should().consumerName("name");
    		then(builder).should().topics(topics);
    		then(builder).should().topicsPattern(topisPattern);
    		then(builder).should().priorityLevel(123);
    		then(builder).should().readCompacted(true);
    		then(builder).should().deadLetterPolicy(new DeadLetterPolicy(1, null, "my-dlt", null, null, null));
    	}
}
