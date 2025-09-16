public class test103 {

    @Test
    	void testRabbitTemplateRetry() {
    		this.contextRunner.withUserConfiguration(TestConfiguration.class)
    			.withPropertyValues("spring.rabbitmq.template.retry.enabled:true",
    					"spring.rabbitmq.template.retry.max-attempts:4",
    					"spring.rabbitmq.template.retry.initial-interval:2000",
    					"spring.rabbitmq.template.retry.multiplier:1.5", "spring.rabbitmq.template.retry.max-interval:5000",
    					"spring.rabbitmq.template.receive-timeout:123", "spring.rabbitmq.template.reply-timeout:456")
    			.run((context) -> {
    				RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
    				assertThat(rabbitTemplate).hasFieldOrPropertyWithValue("receiveTimeout", 123L);
    				assertThat(rabbitTemplate).hasFieldOrPropertyWithValue("replyTimeout", 456L);
    				RetryTemplate retryTemplate = (RetryTemplate) ReflectionTestUtils.getField(rabbitTemplate,
    						"retryTemplate");
    				assertThat(retryTemplate).isNotNull();
    				SimpleRetryPolicy retryPolicy = (SimpleRetryPolicy) ReflectionTestUtils.getField(retryTemplate,
    						"retryPolicy");
    				ExponentialBackOffPolicy backOffPolicy = (ExponentialBackOffPolicy) ReflectionTestUtils
    					.getField(retryTemplate, "backOffPolicy");
    				assertThat(retryPolicy.getMaxAttempts()).isEqualTo(4);
    				assertThat(backOffPolicy.getInitialInterval()).isEqualTo(2000);
    				assertThat(backOffPolicy.getMultiplier()).isEqualTo(1.5);
    				assertThat(backOffPolicy.getMaxInterval()).isEqualTo(5000);
    			});
    	}
}
