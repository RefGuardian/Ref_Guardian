public class test215 {

    /**
     * Configure the specified {@link RabbitTemplate}. The template can be further tuned
     * and default settings can be overridden.
     *
     * @param template the {@link RabbitTemplate} instance to configure
     * @param connectionFactory the {@link ConnectionFactory} to use
     */
    public void configure(RabbitTemplate template, ConnectionFactory connectionFactory) {
        PropertyMapper map = PropertyMapper.get();
        template.setConnectionFactory(connectionFactory);
        if (this.messageConverter != null) {
            template.setMessageConverter(this.messageConverter);
        }
        template.setMandatory(determineMandatoryFlag());
        RabbitProperties.Template templateProperties = this.rabbitProperties.getTemplate();
        if (templateProperties.getRetry().isEnabled()) {
            template.setRetryTemplate(new RetryTemplateFactory(this.retryTemplateCustomizers)
                    .createRetryTemplate(templateProperties.getRetry(), RabbitRetryTemplateCustomizer.Target.SENDER));
        }
        map.from(templateProperties::getReceiveTimeout)
                .whenNonNull()
                .as(Duration::toMillis)
                .to(template::setReceiveTimeout);
        map.from(templateProperties::getReplyTimeout)
                .whenNonNull()
                .as(Duration::toMillis)
                .to(template::setReplyTimeout);
        template.setExchange(templateProperties.getExchange());
        map.from(templateProperties::getRoutingKey).to(template::setRoutingKey);
        if (templateProperties.getDefaultReceiveQueue() != null) {
            template.setDefaultReceiveQueue(templateProperties.getDefaultReceiveQueue());
        }
        template.setObservationEnabled(templateProperties.isObservationEnabled());
        List<Pattern> allowedListPatterns = templateProperties.getAllowedListPatterns();
        if (!CollectionUtils.isEmpty(allowedListPatterns)) {
            setAllowedListPatterns(template.getMessageConverter(), allowedListPatterns);
        }
    }
}
