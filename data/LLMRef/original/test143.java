public class test143 {

    @Test
    		void bind() {
    			Map<String, String> map = new HashMap<>();
    			map.put("spring.pulsar.consumer.name", "my-consumer");
    			map.put("spring.pulsar.consumer.subscription.initial-position", "earliest");
    			map.put("spring.pulsar.consumer.subscription.mode", "nondurable");
    			map.put("spring.pulsar.consumer.subscription.name", "my-subscription");
    			map.put("spring.pulsar.consumer.subscription.topics-mode", "all-topics");
    			map.put("spring.pulsar.consumer.subscription.type", "shared");
    			map.put("spring.pulsar.consumer.topics[0]", "my-topic");
    			map.put("spring.pulsar.consumer.topics-pattern", "my-pattern");
    			map.put("spring.pulsar.consumer.priority-level", "8");
    			map.put("spring.pulsar.consumer.read-compacted", "true");
    			map.put("spring.pulsar.consumer.dead-letter-policy.max-redeliver-count", "4");
    			map.put("spring.pulsar.consumer.dead-letter-policy.retry-letter-topic", "my-retry-topic");
    			map.put("spring.pulsar.consumer.dead-letter-policy.dead-letter-topic", "my-dlt-topic");
    			map.put("spring.pulsar.consumer.dead-letter-policy.initial-subscription-name", "my-initial-subscription");
    			map.put("spring.pulsar.consumer.retry-enable", "true");
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
