public class test219 {

    private ConditionOutcome getMatchOutcome(String[] locations) {
        if (!isJndiAvailable()) {
            return createNoMatchOutcome(ConditionalOnJndi.class, "JNDI environment");
        }
        if (locations.length == 0) {
            return createMatchOutcome(ConditionalOnJndi.class, "JNDI environment");
        }
        JndiLocator locator = getJndiLocator(locations);
        String location = locator.lookupFirstLocation();
        String details = createDetailsString(locations);
        if (location != null) {
            return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class, details)
                    .foundExactly("\"" + location + "\""));
        }
        return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class, details)
                .didNotFind("any matching JNDI location")
                .atAll());
    }

    private String createDetailsString(String[] locations) {
        return "(" + StringUtils.arrayToCommaDelimitedString(locations) + ")";
    }

    private ConditionOutcome createMatchOutcome(Class<?> condition, String reason) {
        return ConditionOutcome.match(ConditionMessage.forCondition(condition).available(reason));
    }

    private ConditionOutcome createNoMatchOutcome(Class<?> condition, String reason) {
        return ConditionOutcome.noMatch(ConditionMessage.forCondition(condition).notAvailable(reason));
    }
}
