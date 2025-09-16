public class test146 {

    @Test
   	@SuppressWarnings("unchecked")
   	void customizeConsumerBuilder() {
   		PulsarProperties properties = new PulsarProperties();
   		List<String> topics = List.of("mytopic");
   		Pattern topisPattern = Pattern.compile("my-pattern");
   		int priorityLevel = 123;
   		boolean readCompacted = true;
   		String deadLetterTopic = "my-dlt";
   		int maxRedeliverCount = 1;
   		
   		properties.getConsumer().setName("name");
   		properties.getConsumer().setTopics(topics);
   		properties.getConsumer().setTopicsPattern(topisPattern);
   		properties.getConsumer().setPriorityLevel(priorityLevel);
   		properties.getConsumer().setReadCompacted(readCompacted);
   		
   		Consumer.DeadLetterPolicy deadLetterPolicy = new Consumer.DeadLetterPolicy();
   		deadLetterPolicy.setDeadLetterTopic(deadLetterTopic);
   		deadLetterPolicy.setMaxRedeliverCount(maxRedeliverCount);
   		
   		properties.getConsumer().setDeadLetterPolicy(deadLetterPolicy);
   		
   		ConsumerBuilder<Object> builder = mock(ConsumerBuilder.class);
   		new PulsarPropertiesMapper(properties).customizeConsumerBuilder(builder);
   		
   		then(builder).should().consumerName("name");
   		then(builder).should().topics(topics);
   		then(builder).should().topicsPattern(topisPattern);
   		then(builder).should().priorityLevel(priorityLevel);
   		then(builder).should().readCompacted(readCompacted);
   		then(builder).should().deadLetterPolicy(new DeadLetterPolicy(maxRedeliverCount, null, deadLetterTopic, null, null, null));
   	}
}
