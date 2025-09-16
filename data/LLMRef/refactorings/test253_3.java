public class test253 {

    private void addNonMatchLogMessage(StringBuilder message, String source,
    			ConditionAndOutcomes conditionAndOutcomes) {
    		message.append(String.format("%n   %s:%n", source));

    		List<ConditionAndOutcome> matches = new ArrayList<>();
    		List<ConditionAndOutcome> nonMatches = new ArrayList<>();
    		
    		processConditionAndOutcomes(conditionAndOutcomes, matches, nonMatches);

    		message.append(String.format("      Did not match:%n"));
    		logNonMatches(message, nonMatches);

    		if (!matches.isEmpty()) {
    			message.append(String.format("      Matched:%n"));
    			logMatches(message, matches);
    		}
    	}
    	
    private void processConditionAndOutcomes(ConditionAndOutcomes conditionAndOutcomes,
                                              List<ConditionAndOutcome> matches,
                                              List<ConditionAndOutcome> nonMatches) {
        for (ConditionAndOutcome conditionAndOutcome : conditionAndOutcomes) {
			if (conditionAndOutcome.getOutcome().isMatch()) {
				matches.add(conditionAndOutcome);
			}
			else {
				nonMatches.add(conditionAndOutcome);
			}
		}
    }

    private void logNonMatches(StringBuilder message, List<ConditionAndOutcome> nonMatches) {
        for (ConditionAndOutcome nonMatch : nonMatches) {
			logConditionAndOutcome(message, "         ", nonMatch);
		}
    }

    private void logMatches(StringBuilder message, List<ConditionAndOutcome> matches) {
        for (ConditionAndOutcome match : matches) {
			logConditionAndOutcome(message, "         ", match);
		}
    }

    // Original logConditionAndOutcome method from the original code but kept for completeness
    private void logConditionAndOutcome(StringBuilder message, String prefix, ConditionAndOutcome conditionAndOutcome) {
        // Implementation details of this method
    }
}
