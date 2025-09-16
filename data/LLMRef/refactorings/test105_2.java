public class test105 {

    private static final String SPRING_RABBITMQ_LISTENER_TYPE = "spring.rabbitmq.listener.type";
    private static final String SPRING_RABBITMQ_LISTENER_TYPE_VALUE_DIRECT = "direct";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_CONSUMERS_PER_QUEUE = "spring.rabbitmq.listener.direct.consumers-per-queue";
    private static final int DEFAULT_CONSUMERS_PER_QUEUE = 5;
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_MISSING_QUEUES_FATAL = "spring.rabbitmq.listener.direct.missing-queues-fatal";
    private static final boolean DEFAULT_MISSING_QUEUES_FATAL = true;
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_OBSERVATION_ENABLED = "spring.rabbitmq.listener.direct.observation-enabled";
    private static final boolean DEFAULT_OBSERVATION_ENABLED = true;

    @Test
    	void testDirectRabbitListenerContainerFactoryWithCustomSettings() {
    		this.contextRunner
    			.withUserConfiguration(MessageConvertersConfiguration.class, MessageRecoverersConfiguration.class)
    			.withPropertyValues(SPRING_RABBITMQ_LISTENER_TYPE + ":" + SPRING_RABBITMQ_LISTENER_TYPE_VALUE_DIRECT,
    					"spring.rabbitmq.listener.direct.retry.enabled:true",
    					"spring.rabbitmq.listener.direct.retry.max-attempts:4",
    					"spring.rabbitmq.listener.direct.retry.initial-interval:2000",
    					"spring.rabbitmq.listener.direct.retry.multiplier:1.5",
    					"spring.rabbitmq.listener.direct.retry.max-interval:5000",
    					"spring.rabbitmq.listener.direct.auto-startup:false",
    					"spring.rabbitmq.listener.direct.acknowledge-mode:manual",
    					SPRING_RABBITMQ_LISTENER_DIRECT_CONSUMERS_PER_QUEUE + ":" + DEFAULT_CONSUMERS_PER_QUEUE,
    					"spring.rabbitmq.listener.direct.prefetch:40",
    					"spring.rabbitmq.listener.direct.default-requeue-rejected:false",
    					"spring.rabbitmq.listener.direct.idle-event-interval:5",
    					SPRING_RABBITMQ_LISTENER_DIRECT_MISSING_QUEUES_FATAL + ":" + DEFAULT_MISSING_QUEUES_FATAL,
    					"spring.rabbitmq.listener.direct.force-stop:true",
    					SPRING_RABBITMQ_LISTENER_DIRECT_OBSERVATION_ENABLED + ":" + DEFAULT_OBSERVATION_ENABLED)
    			.run((context) -> {
    				DirectRabbitListenerContainerFactory rabbitListenerContainerFactory = context
    					.getBean("rabbitListenerContainerFactory", DirectRabbitListenerContainerFactory.class);
    				assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue(SPRING_RABBITMQ_LISTENER_DIRECT_CONSUMERS_PER_QUEUE, DEFAULT_CONSUMERS_PER_QUEUE);
    				assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue(SPRING_RABBITMQ_LISTENER_DIRECT_MISSING_QUEUES_FATAL, DEFAULT_MISSING_QUEUES_FATAL);
    				assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue(SPRING_RABBITMQ_LISTENER_DIRECT_OBSERVATION_ENABLED, DEFAULT_OBSERVATION_ENABLED);
    				checkCommonProps(context, rabbitListenerContainerFactory);
    			});
    	}
}
