public class test219 {

    private ConditionOutcome getMatchOutcome(String[] locations) {
    		if (!isJndiAvailable()) {
    			return ConditionOutcome
    				.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class).notAvailable("JNDI environment"));
    		}
    		if (locations.length == 0) {
    			return ConditionOutcome
    				.match(ConditionMessage.forCondition(ConditionalOnJndi.class).available("JNDI environment"));
    		}
    		JndiLocator locator = getJndiLocator(locations);
    		String location = locator.lookupFirstLocation();
    		String details = "(" + StringUtils.arrayToCommaDelimitedString(locations) + ")";
    		if (location != null) {
    			return ConditionOutcome.match(ConditionMessage.forCondition(ConditionalOnJndi.class, details)
    				.foundExactly("\"" + location + "\""));
    		}
    		return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnJndi.class, details)
    			.didNotFind("any matching JNDI location")
    			.atAll());
    	}
}
