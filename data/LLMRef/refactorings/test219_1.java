public class test219 {

    private ConditionOutcome getMatchOutcome(String[] locations) {
        if (!isJndiAvailable()) {
            return getNoMatchOutcome();
        }
        if (locations.length == 0) {
            return getMatchOutcomeForCondition();
        }
        JndiLocator locator = getJndiLocator(locations);
        String location = locator.lookupFirstLocation();
        String details = getDetails(locations);
        if (location != null) {
            return getMatchOutcomeForJndi(details, location);
        }
        return getNoMatchOutcomeForJndi(details);
    }

    private ConditionOutcome getMatchOutcomeForJndi(String details, String location) {
        return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class, details)
                .foundExactly("\"" + location + "\""));
    }

    private ConditionOutcome getNoMatchOutcomeForJndi(String details) {
        return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class, details)
                .didNotFind("any matching JNDI location")
                .atAll());
    }

    private String getDetails(String[] locations) {
        return "(" + StringUtils.arrayToCommaDelimitedString(locations) + ")";
    }

    private ConditionOutcome getMatchOutcomeForCondition() {
        return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class).available("JNDI environment"));
    }

    private ConditionOutcome getNoMatchOutcome() {
        return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class).notAvailable("JNDI environment"));
    }
}
