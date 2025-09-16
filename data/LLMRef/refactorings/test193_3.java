public class test193 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) context.getEnvironment();
        boolean fixed = getEnabledProperty(environment, "strategy.fixed.", false);
        boolean content = getEnabledProperty(environment, "strategy.content.", false);
        Boolean chain = getEnabledProperty(environment, "", null);
        Boolean match = Chain.getEnabled(fixed, content, chain);
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnEnabledResourceChain.class);
        handleMatchOutcome(match, message);
    }

    private void handleMatchOutcome(Boolean match, ConditionMessage.Builder message) {
        if (match == null) {
            if (ClassUtils.isPresent(WEBJAR_VERSION_LOCATOR, getClass().getClassLoader())) {
                return ConditionOutcome.match(message.found("class").items(WEBJAR_VERSION_LOCATOR));
            }
            if (ClassUtils.isPresent(WEBJAR_ASSET_LOCATOR, getClass().getClassLoader())) {
                return ConditionOutcome.match(message.found("class").items(WEBJAR_ASSET_LOCATOR));
            }
            return ConditionOutcome.noMatch(message.didNotFind("class").items(WEBJAR_VERSION_LOCATOR));
        }
        if (match) {
            return ConditionOutcome.match(message.because("enabled"));
        }
        return ConditionOutcome.noMatch(message.because("disabled"));
    }
}
