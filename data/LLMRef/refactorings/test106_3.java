public class test106 {

    private void checkCommonProps(AssertableApplicationContext context,
                                   AbstractRabbitListenerContainerFactory<?> containerFactory) {
        assertContainerFactoryProperties(containerFactory, context);
    }

    private void assertContainerFactoryProperties(AbstractRabbitListenerContainerFactory<?> containerFactory,
                                                  AssertableApplicationContext context) {
        assertThat(containerFactory).hasFieldOrPropertyWithValue("autoStartup", Boolean.FALSE);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("acknowledgeMode", AcknowledgeMode.MANUAL);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("prefetchCount", 40);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("messageConverter",context.getBean("myMessageConverter"));
        assertThat(containerFactory).hasFieldOrPropertyWithValue("defaultRequeueRejected", Boolean.FALSE);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("idleEventInterval", 5L);
        assertThat(containerFactory).hasFieldOrPropertyWithValue("forceStop", true);
        assertAdviceChain(containerFactory, context);
        assertRetryTemplate(containerFactory, context);
    }

    private void assertAdviceChain(AbstractRabbitListenerContainerFactory<?> containerFactory,
                                   AssertableApplicationContext context) {
        Advice[] adviceChain = containerFactory.getAdviceChain();
        assertThat(adviceChain).isNotNull();
        assertThat(adviceChain).hasSize(1);
        Advice advice = adviceChain[0];
        assertMessageRecovererAndInvocation(containerFactory, context, advice);
    }

    private void assertMessageRecovererAndInvocation(AbstractRabbitListenerContainerFactory<?> containerFactory,
                                                     AssertableApplicationContext context, Advice advice) {
        MessageRecoverer messageRecoverer = context.getBean("myMessageRecoverer", MessageRecoverer.class);
        MethodInvocationRecoverer<?> mir = (MethodInvocationRecoverer<?>) ReflectionTestUtils.getField(advice,
                "recoverer");
        Message message = mock(Message.class);
        Exception ex = new Exception("test");
        mir.recover(new Object[] { "foo", message }, ex);
        then(messageRecoverer).should().recover(message, ex);
    }

    private void assertRetryTemplate(AbstractRabbitListenerContainerFactory<?> containerFactory,
                                     AssertableApplicationContext context) {
        RetryTemplate retryTemplate = (RetryTemplate) ReflectionTestUtils.getField(containerFactory, "retryOperations");
        assertThat(retryTemplate).isNotNull();
        SimpleRetryPolicy retryPolicy = (SimpleRetryPolicy) ReflectionTestUtils.getField(retryTemplate, "retryPolicy");
        ExponentialBackOffPolicy backOffPolicy = (ExponentialBackOffPolicy) ReflectionTestUtils.getField(retryTemplate,
                "backOffPolicy");
        assertRetryPolicyProperties(retryPolicy);
        assertBackOffPolicyProperties(backOffPolicy);
    }

    private void assertRetryPolicyProperties(SimpleRetryPolicy retryPolicy) {
        assertThat(retryPolicy.getMaxAttempts()).isEqualTo(4);
    }

    private void assertBackOffPolicyProperties(ExponentialBackOffPolicy backOffPolicy) {
        assertThat(backOffPolicy.getInitialInterval()).isEqualTo(2000);
        assertThat(backOffPolicy.getMultiplier()).isEqualTo(1.5);
        assertThat(backOffPolicy.getMaxInterval()).isEqualTo(5000);
    }
}
