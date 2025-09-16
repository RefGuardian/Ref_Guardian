public class test115 {

    @SuppressWarnings("unchecked")
    	@Test
    	void testEndToEndWithRetryTopics() throws Exception {
    		load(KafkaConfig.class, "spring.kafka.bootstrap-servers:" + getEmbeddedKafkaBrokersAsString(),
    				"spring.kafka.consumer.group-id=testGroup", "spring.kafka.retry.topic.enabled=true",
    				"spring.kafka.retry.topic.attempts=5", "spring.kafka.retry.topic.delay=100ms",
    				"spring.kafka.retry.topic.multiplier=2", "spring.kafka.retry.topic.max-delay=300ms",
    				"spring.kafka.consumer.auto-offset-reset=earliest");
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
