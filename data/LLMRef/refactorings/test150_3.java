public class test150 {

    @Test		
		void injectsExpectedBeans() {
			ReactivePulsarClient client = mock(ReactivePulsarClient.class);
			ReactiveMessageSenderCache cache = mock(ReactiveMessageSenderCache.class);
			this.contextRunner
				.withPropertyValues("spring.pulsar.producer.topic-name=test-topic")
				.withBean("customReactivePulsarClient", ReactivePulsarClient.class, () -> client)
				.withBean("customReactiveMessageSenderCache", ReactiveMessageSenderCache.class, () -> cache)
				.run((context) -> {
					DefaultReactivePulsarSenderFactory<?> senderFactory = context
						.getBean(DefaultReactivePulsarSenderFactory.class);
					assertThat(senderFactory)
						.extracting("reactivePulsarClient", InstanceOfAssertFactories.type(ReactivePulsarClient.class))
						.isSameAs(client);
					assertThat(senderFactory)
						.extracting("reactiveMessageSenderCache",
								InstanceOfAssertFactories.type(ReactiveMessageSenderCache.class))
						.isSameAs(cache);
					assertThat(senderFactory)
					.extracting("topicResolver", InstanceOfAssertFactories.type(TopicResolver.class))
					.isSameAs(context.getBean(TopicResolver.class));
					assertThat(senderFactory).extracting("topicBuilder").isNotNull();
				});
		}
}
