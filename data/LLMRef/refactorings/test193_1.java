public class test193 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) context.getEnvironment();
        boolean fixed = getEnabledProperty(environment, "strategy.fixed.", false);
        boolean content = getEnabledProperty(environment, "strategy.content.", false);
        Boolean chain = getEnabledProperty(environment, "", null);
        Boolean match = Chain.getEnabled(fixed, content, chain);
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnEnabledResourceChain.class);
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

    private boolean getEnabledProperty(ConfigurableEnvironment environment, String s, boolean b) {
        // Extracted method
    }

    private boolean getEnabledProperty(ConfigurableEnvironment environment, String s, boolean b) {
        // Extracted method
    }

    private class Chain {
        // Extracted class
    }

    private class ConditionalOnEnabledResourceChain {
        // Extracted class
    }

    private static final String WEBJAR_VERSION_LOCATOR = "WEBJAR_VERSION_LOCATOR";
    private static final String WEBJAR_ASSET_LOCATOR = "WEBJAR_ASSET_LOCATOR";

}
