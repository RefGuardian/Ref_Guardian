public class test253 {

    private void addNonMatchLogMessage(StringBuilder message, String source,
    			ConditionAndOutcomes conditionAndOutcomes) {
    		message.append(String.format("%n   %s:%n", source));
    		List<ConditionAndOutcome> matches = new ArrayList<>();
    		List<ConditionAndOutcome> nonMatches = new ArrayList<>();
    		for (ConditionAndOutcome conditionAndOutcome : conditionAndOutcomes) {
    			if (conditionAndOutcome.getOutcome().isMatch()) {
    				matches.add(conditionAndOutcome);
    			}
    			else {
    				nonMatches.add(conditionAndOutcome);
    			}
    		}
    		message.append(String.format("      Did not match:%n"));
    		for (ConditionAndOutcome nonMatch : nonMatches) {
    			logConditionAndOutcome(message, "         ", nonMatch);
    		}
    		if (!matches.isEmpty()) {
    			message.append(String.format("      Matched:%n"));
    			for (ConditionAndOutcome match : matches) {
    				logConditionAndOutcome(message, "         ", match);
    			}
    		}
    	}
}
