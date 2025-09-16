public class test104 {

    private static final int RETRY_MAX_ATTEMPTS = 4;
    private static final int RETRY_INITIAL_INTERVAL = 2000;
    private static final double RETRY_MULTIPLIER = 1.5;
    private static final int RETRY_MAX_INTERVAL = 5000;
    private static final int CONCURRENCY = 5;
    private static final int MAX_CONCURRENCY = 10;
    private static final int PREFETCH = 40;
    private static final int IDLE_EVENT_INTERVAL = 5;
    private static final int BATCH_SIZE = 20;

    @Test
    void testSimpleRabbitListenerContainerFactoryWithCustomSettings() {
        this.contextRunner
            .withUserConfiguration(MessageConvertersConfiguration.class, MessageRecoverersConfiguration.class)
            .withPropertyValues("spring.rabbitmq.listener.simple.retry.enabled:true",
                "spring.rabbitmq.listener.simple.retry.max-attempts:" + RETRY_MAX_ATTEMPTS,
                "spring.rabbitmq.listener.simple.retry.initial-interval:" + RETRY_INITIAL_INTERVAL,
                "spring.rabbitmq.listener.simple.retry.multiplier:" + RETRY_MULTIPLIER,
                "spring.rabbitmq.listener.simple.retry.max-interval:" + RETRY_MAX_INTERVAL,
                "spring.rabbitmq.listener.simple.auto-startup:false",
                "spring.rabbitmq.listener.simple.acknowledge-mode:manual",
                "spring.rabbitmq.listener.simple.concurrency:" + CONCURRENCY,
                "spring.rabbitmq.listener.simple.max-concurrency:" + MAX_CONCURRENCY, "spring.rabbitmq.listener.simple.prefetch:" + PREFETCH,
                "spring.rabbitmq.listener.simple.default-requeue-rejected:false",
                "spring.rabbitmq.listener.simple.idle-event-interval:" + IDLE_EVENT_INTERVAL,
                "spring.rabbitmq.listener.simple.batch-size:" + BATCH_SIZE,
                "spring.rabbitmq.listener.simple.missing-queues-fatal:false",
                "spring.rabbitmq.listener.simple.force-stop:true",
                "spring.rabbitmq.listener.simple.observation-enabled:true")
            .run((context) -> {
                SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = context
                    .getBean("rabbitListenerContainerFactory", SimpleRabbitListenerContainerFactory.class);
                assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("concurrentConsumers", CONCURRENCY);
                assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("maxConcurrentConsumers", MAX_CONCURRENCY);
                assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("batchSize", BATCH_SIZE);
                assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("missingQueuesFatal", false);
                assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("observationEnabled", true);
                checkCommonProps(context, rabbitListenerContainerFactory);
            });
    }
}
