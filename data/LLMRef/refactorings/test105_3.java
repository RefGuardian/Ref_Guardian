public class test105 {

    private static final String SPRING_RABBITMQ_LISTENER_TYPE = "spring.rabbitmq.listener.type";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_ENABLED = "spring.rabbitmq.listener.direct.retry.enabled";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_MAX_ATTEMPTS = "spring.rabbitmq.listener.direct.retry.max-attempts";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_INITIAL_INTERVAL = "spring.rabbitmq.listener.direct.retry.initial-interval";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_MULTIPLIER = "spring.rabbitmq.listener.direct.retry.multiplier";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_MAX_INTERVAL = "spring.rabbitmq.listener.direct.retry.max-interval";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_AUTO_STARTUP = "spring.rabbitmq.listener.direct.auto-startup";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_ACKNOWLEDGE_MODE = "spring.rabbitmq.listener.direct.acknowledge-mode";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_CONSUMERS_PER_QUEUE = "spring.rabbitmq.listener.direct.consumers-per-queue";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_PREFETCH = "spring.rabbitmq.listener.direct.prefetch";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_DEFAULT_REQUEUE_REJECTED = "spring.rabbitmq.listener.direct.default-requeue-rejected";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_IDLE_EVENT_INTERVAL = "spring.rabbitmq.listener.direct.idle-event-interval";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_MISSING_QUEUES_FATAL = "spring.rabbitmq.listener.direct.missing-queues-fatal";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_FORCE_STOP = "spring.rabbitmq.listener.direct.force-stop";
    private static final String SPRING_RABBITMQ_LISTENER_DIRECT_OBSERVATION_ENABLED = "spring.rabbitmq.listener.direct.observation-enabled";

    @Test
    void testDirectRabbitListenerContainerFactoryWithCustomSettings() {
        this.contextRunner
                .withUserConfiguration(MessageConvertersConfiguration.class, MessageRecoverersConfiguration.class)
                .withPropertyValues(SPRING_RABBITMQ_LISTENER_TYPE + ":direct",
                        SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_ENABLED + ":true",
                        SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_MAX_ATTEMPTS + ":4",
                        SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_INITIAL_INTERVAL + ":2000",
                        SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_MULTIPLIER + ":1.5",
                        SPRING_RABBITMQ_LISTENER_DIRECT_RETRY_MAX_INTERVAL + ":5000",
                        SPRING_RABBITMQ_LISTENER_DIRECT_AUTO_STARTUP + ":false",
                        SPRING_RABBITMQ_LISTENER_DIRECT_ACKNOWLEDGE_MODE + ":manual",
                        SPRING_RABBITMQ_LISTENER_DIRECT_CONSUMERS_PER_QUEUE + ":5",
                        SPRING_RABBITMQ_LISTENER_DIRECT_PREFETCH + ":40",
                        SPRING_RABBITMQ_LISTENER_DIRECT_DEFAULT_REQUEUE_REJECTED + ":false",
                        SPRING_RABBITMQ_LISTENER_DIRECT_IDLE_EVENT_INTERVAL + ":5",
                        SPRING_RABBITMQ_LISTENER_DIRECT_MISSING_QUEUES_FATAL + ":true",
                        SPRING_RABBITMQ_LISTENER_DIRECT_FORCE_STOP + ":true",
                        SPRING_RABBITMQ_LISTENER_DIRECT_OBSERVATION_ENABLED + ":true")
                .run((context) -> {
                    DirectRabbitListenerContainerFactory rabbitListenerContainerFactory = context
                            .getBean("rabbitListenerContainerFactory", DirectRabbitListenerContainerFactory.class);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue(SPRING_RABBITMQ_LISTENER_DIRECT_CONSUMERS_PER_QUEUE, 5);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue(SPRING_RABBITMQ_LISTENER_DIRECT_MISSING_QUEUES_FATAL, true);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue(SPRING_RABBITMQ_LISTENER_DIRECT_OBSERVATION_ENABLED, true);
                    checkCommonProps(context, rabbitListenerContainerFactory);
                });
    }
}
