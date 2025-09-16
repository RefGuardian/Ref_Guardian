public class test214 {
    
    protected void configure(T factory, ConnectionFactory connectionFactory, RabbitProperties.AmqpContainer configuration) {
        Assert.notNull(factory, "'factory' must not be null");
        Assert.notNull(connectionFactory, "'connectionFactory' must not be null");
        Assert.notNull(configuration, "'configuration' must not be null");
        factory.setConnectionFactory(connectionFactory);
        if (this.messageConverter != null) {
            factory.setMessageConverter(this.messageConverter);
        }
        factory.setAutoStartup(configuration.isAutoStartup());
        if (configuration.getAcknowledgeMode() != null) {
            factory.setAcknowledgeMode(configuration.getAcknowledgeMode());
        }
        if (configuration.getPrefetch() != null) {
            factory.setPrefetchCount(configuration.getPrefetch());
        }
        if (configuration.getDefaultRequeueRejected() != null) {
            factory.setDefaultRequeueRejected(configuration.getDefaultRequeueRejected());
        }
        if (configuration.getIdleEventInterval() != null) {
            factory.setIdleEventInterval(configuration.getIdleEventInterval().toMillis());
        }
        factory.setMissingQueuesFatal(configuration.isMissingQueuesFatal());
        factory.setDeBatchingEnabled(configuration.isDeBatchingEnabled());
        factory.setForceStop(configuration.isForceStop());
        if (this.taskExecutor != null) {
            factory.setTaskExecutor(this.taskExecutor);
        }
        factory.setObservationEnabled(configuration.isObservationEnabled());
        ListenerRetry retryConfig = configuration.getRetry();
        if (retryConfig.isEnabled()) {
            RetryInterceptorBuilder<?, ?> builder = (retryConfig.isStateless()) ? RetryInterceptorBuilder.stateless()
                : RetryInterceptorBuilder.stateful();
            RetryTemplate retryTemplate = new RetryTemplateFactory(this.retryTemplateCustomizers)
                .createRetryTemplate(retryConfig, RabbitRetryTemplateCustomizer.Target.LISTENER);
            builder.retryOperations(retryTemplate);
            MessageRecoverer recoverer = (this.messageRecoverer != null) ? this.messageRecoverer
                : new RejectAndDontRequeueRecoverer();
            builder.recoverer(recoverer);
            factory.setAdviceChain(builder.build());
        }
    }
}
