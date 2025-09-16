public class test243 {

    private static final String FACTORY_PARAM = "'factory' must not be null";
    private static final String CONNECTION_FACTORY_PARAM = "'connectionFactory' must not be null";

    /**
     * Configure the specified jms listener container factory. The factory can be further
     * tuned and default settings can be overridden.
     * @param factory the {@link DefaultJmsListenerContainerFactory} instance to configure
     * @param connectionFactory the {@link ConnectionFactory} to use
     */
    public void configure(DefaultJmsListenerContainerFactory factory, ConnectionFactory connectionFactory) {
        Assert.notNull(factory, FACTORY_PARAM);
        Assert.notNull(connectionFactory, CONNECTION_FACTORY_PARAM);
        JmsProperties.Listener listenerProperties = this.jmsProperties.getListener();
        Session sessionProperties = listenerProperties.getSession();
        factory.setConnectionFactory(connectionFactory);
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.jmsProperties::isPubSubDomain).to(factory::setPubSubDomain);
        map.from(this.jmsProperties::isSubscriptionDurable).to(factory::setSubscriptionDurable);
        map.from(this.jmsProperties::getClientId).to(factory::setClientId);
        map.from(this.transactionManager).to(factory::setTransactionManager);
        map.from(this.destinationResolver).to(factory::setDestinationResolver);
        map.from(this.messageConverter).to(factory::setMessageConverter);
        map.from(this.exceptionListener).to(factory::setExceptionListener);
        map.from(sessionProperties.getAcknowledgeMode()::getMode).to(factory::setSessionAcknowledgeMode);
        if (this.transactionManager == null && sessionProperties.getTransacted() == null) {
            factory.setSessionTransacted(true);
        }
        map.from(this.observationRegistry).to(factory::setObservationRegistry);
        map.from(sessionProperties::getTransacted).to(factory::setSessionTransacted);
        map.from(listenerProperties::isAutoStartup).to(factory::setAutoStartup);
        map.from(listenerProperties::formatConcurrency).to(factory::setConcurrency);
        map.from(listenerProperties::getReceiveTimeout).as(Duration::toMillis).to(factory::setReceiveTimeout);
        map.from(listenerProperties::getMaxMessagesPerTask).to(factory::setMaxMessagesPerTask);
    }
}
