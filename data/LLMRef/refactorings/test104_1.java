public class test104 {

    @Test
    void testSimpleRabbitListenerContainerFactoryWithCustomSettings() {
        this.contextRunner
                .withUserConfiguration(MessageConvertersConfiguration.class, MessageRecoverersConfiguration.class)
                .withPropertyValues("spring.rabbitmq.listener.simple.retry.enabled:true",
                        "spring.rabbitmq.listener.simple.retry.max-attempts:4",
                        "spring.rabbitmq.listener.simple.retry.initial-interval:2000",
                        "spring.rabbitmq.listener.simple.retry.multiplier:1.5",
                        "spring.rabbitmq.listener.simple.retry.max-interval:5000",
                        "spring.rabbitmq.listener.simple.auto-startup:false",
                        "spring.rabbitmq.listener.simple.acknowledge-mode:manual",
                        "spring.rabbitmq.listener.simple.concurrency:5",
                        "spring.rabbitmq.listener.simple.max-concurrency:10", "spring.rabbitmq.listener.simple.prefetch:40",
                        "spring.rabbitmq.listener.simple.default-requeue-rejected:false",
                        "spring.rabbitmq.listener.simple.idle-event-interval:5",
                        "spring.rabbitmq.listener.simple.batch-size:20",
                        "spring.rabbitmq.listener.simple.missing-queues-fatal:false",
                        "spring.rabbitmq.listener.simple.force-stop:true",
                        "spring.rabbitmq.listener.simple.observation-enabled:true")
                .run((context) -> {
                    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = context
                            .getBean("rabbitListenerContainerFactory", SimpleRabbitListenerContainerFactory.class);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("concurrentConsumers", 5);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("maxConcurrentConsumers", 10);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("batchSize", 20);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("missingQueuesFatal", false);
                    assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("observationEnabled", true);
                    checkCommonProps(context, rabbitListenerContainerFactory);
                });
    }

    private void checkCommonProps(ConfigurableApplicationContext context, SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory) {
        // Method implementation Here
    }
}
