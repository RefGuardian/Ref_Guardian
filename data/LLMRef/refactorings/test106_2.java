public class test106 {

    private void checkCommonProps(AssertableApplicationContext context,
            AbstractRabbitListenerContainerFactory<?> containerFactory) {
        assertContainerFactoryProps(containerFactory);
        Advice[] adviceChain = containerFactory.getAdviceChain();
        assertThat(adviceChain).isNotNull();
        assertThat(adviceChain).hasSize(1);
        Advice advice = adviceChain[0];
        MessageRecoverer messageRecoverer = context.getBean("myMessageRecoverer", MessageRecoverer.class);
        MethodInvocationRecoverer<?> mir = (MethodInvocationRecoverer<?>) ReflectionTestUtils.getField(advice,
                "recoverer");
        Message message = mock(Message.class);
        Exception ex = new Exception("test");
        mir.recover(new Object[] { "foo", message }, ex);
        then(messageRecoverer).should().recover(message, ex);
        RetryTemplate retryTemplate = (RetryTemplate) ReflectionTestUtils.getField(advice, "retryOperations");
        assertThat(retryTemplate).isNotNull();
        assertRetryTemplateProps(retryTemplate);
    }

    private void assertContainerFactoryProps(AbstractRabbitListenerContainerFactory<?> containerFactory) {
        assertThat(containerFactory).hasFieldOrPropertyWithValue("autoStartup", Boolean.FALSE);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("acknowledgeMode", AcknowledgeMode.MANUAL);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("prefetchCount", 40);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("messageConverter",
                context.getBean("myMessageConverter"));
        assertThat(containerFactory).hasFieldOrPropertyWithValue("defaultRequeueRejected", Boolean.FALSE);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("idleEventInterval", 5L);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("forceStop", true);
    }

    private void assertRetryTemplateProps(RetryTemplate retryTemplate) {
        SimpleRetryPolicy retryPolicy = (SimpleRetryPolicy) ReflectionTestUtils.getField(retryTemplate, "retryPolicy");
        ExponentialBackOffPolicy backOffPolicy = (ExponentialBackOffPolicy) ReflectionTestUtils.getField(retryTemplate,
                "backOffPolicy");
        assertThat(retryPolicy.getMaxAttempts()).isEqualTo(4);
        assertThat(backOffPolicy.getInitialInterval()).isEqualTo(2000);
        assertThat(backOffPolicy.getMultiplier()).isEqualTo(1.5);
        assertThat(backOffPolicy.getMaxInterval()).isEqualTo(5000);
    }
}
