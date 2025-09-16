public class test103 {

    private static final int MAX_ATTEMPTS = 4;
    private static final int INITIAL_INTERVAL = 2000;
    private static final double MULTIPLIER = 1.5;
    private static final int MAX_INTERVAL = 5000;
    private static final long RECEIVE_TIMEOUT = 123;
    private static final long REPLY_TIMEOUT = 456;

    @Test
    void testRabbitTemplateRetry() {
        this.contextRunner.withUserConfiguration(TestConfiguration.class)
                .withPropertyValues("spring.rabbitmq.template.retry.enabled:true",
                        "spring.rabbitmq.template.retry.max-attempts:" + MAX_ATTEMPTS,
                        "spring.rabbitmq.template.retry.initial-interval:" + INITIAL_INTERVAL,
                        "spring.rabbitmq.template.retry.multiplier:" + MULTIPLIER, "spring.rabbitmq.template.retry.max-interval:" + MAX_INTERVAL,
                        "spring.rabbitmq.template.receive-timeout:" + RECEIVE_TIMEOUT, "spring.rabbitmq.template.reply-timeout:" + REPLY_TIMEOUT)
                .run((context) -> {
                    RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
                    assertThat(rabbitTemplate).hasFieldOrPropertyWithValue("receiveTimeout", RECEIVE_TIMEOUT);
                    assertThat(rabbitTemplate).hasFieldOrPropertyWithValue("replyTimeout", REPLY_TIMEOUT);
                    RetryTemplate retryTemplate = (RetryTemplate) ReflectionTestUtils.getField(rabbitTemplate,
                            "retryTemplate");
                    assertThat(retryTemplate).isNotNull();
                    SimpleRetryPolicy retryPolicy = (SimpleRetryPolicy) ReflectionTestUtils.getField(retryTemplate,
                            "retryPolicy");
                    ExponentialBackOffPolicy backOffPolicy = (ExponentialBackOffPolicy) ReflectionTestUtils
                            .getField(retryTemplate, "backOffPolicy");
                    assertThat(retryPolicy.getMaxAttempts()).isEqualTo(MAX_ATTEMPTS);
                    assertThat(backOffPolicy.getInitialInterval()).isEqualTo(INITIAL_INTERVAL);
                    assertThat(backOffPolicy.getMultiplier()).isEqualTo(MULTIPLIER);
                    assertThat(backOffPolicy.getMaxInterval()).isEqualTo(MAX_INTERVAL);
                });
    }
}
