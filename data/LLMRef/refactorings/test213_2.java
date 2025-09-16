public class test213 {

    RetryTemplate createRetryTemplate(RabbitProperties.Retry properties, RabbitRetryTemplateCustomizer.Target target) {
        PropertyMapper map = PropertyMapper.get();
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        map.map(properties::getMaxAttempts, policy::setMaxAttempts);
        template.setRetryPolicy(policy);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        map.from(properties::getInitialInterval)
            .whenNonNull()
            .as(Duration::toMillis)
            .to(backOffPolicy::setInitialInterval);
        map.map(properties::getMultiplier, backOffPolicy::setMultiplier);
        map.from(properties::getMaxInterval)
            .whenNonNull()
            .as(Duration::toMillis)
            .to(backOffPolicy::setMaxInterval);
        template.setBackOffPolicy(backOffPolicy);
        if (this.customizers != null) {
            for (RabbitRetryTemplateCustomizer customizer : this.customizers) {
                customizer.customize(target, template);
            }
        }
        return template;
    }
}
