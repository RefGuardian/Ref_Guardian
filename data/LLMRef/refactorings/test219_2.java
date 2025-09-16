public class test219 {

    private ConditionOutcome getMatchOutcome(String[] locations) {
    	if (!isJndiAvailable()) {
    		return createNoMatchOutcome(locations);
    	}
    	if (locations.length == 0) {
    		return createMatchOutcome();
    	}
    	JndiLocator locator = getJndiLocator(locations);
    	String location = locator.lookupFirstLocation();
    	String details = createDetails(locations);
    	if (location != null) {
    		return createMatchOutcomeWithDetails(location, details);
    	}
    	return createNoMatchOutcomeWithDetails(details);
    }

    private ConditionOutcome createNoMatchOutcome(String[] locations) {
    	return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class).notAvailable("JNDI environment"));
    }

    private ConditionOutcome createMatchOutcome() {
    	return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class).available("JNDI environment"));
    }

    private String createDetails(String[] locations) {
    	return "(" + StringUtils.arrayToCommaDelimitedString(locations) + ")";
    }

    private ConditionOutcome createMatchOutcomeWithDetails(String location, String details) {
    	return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class, details).foundExactly("\"" + location + "\""));
    }

    private ConditionOutcome createNoMatchOutcomeWithDetails(String details) {
    	return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class, details).didNotFind("any matching JNDI location").atAll());
    }
}
