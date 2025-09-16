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
		customizeBuilder(properties, builder);
		verifyConsumerBuilderAssertions(builder, topics, topisPattern);
	}

	private void customizeBuilder(PulsarProperties properties, ConsumerBuilder<Object> builder) {
		new PulsarPropertiesMapper(properties).customizeConsumerBuilder(builder);
	}

	private void verifyConsumerBuilderAssertions(ConsumerBuilder<Object> builder, List<String> topics, Pattern topisPattern) {
		verify(builder).consumerName("name");
		verify(builder).topics(topics);
		verify(builder).topicsPattern(topisPattern);
		verify(builder).priorityLevel(123);
		verify(builder).readCompacted(true);
		verify(builder).deadLetterPolicy(new DeadLetterPolicy(1, null, "my-dlt", null, null, null));
	}
}
