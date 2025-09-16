public class test253 {

    private void addNonMatchLogMessage(StringBuilder message, String source,
                ConditionAndOutcomes conditionAndOutcomes) {
        message.append(String.format("%n   %s:%n", source));
        List<ConditionAndOutcome> matches = new ArrayList<>();
        List<ConditionAndOutcome> nonMatches = new ArrayList<>();
        extractMatchesAndNonMatches(conditionAndOutcomes, matches, nonMatches);
        logNonMatches(message, nonMatches);
        logMatches(message, matches);
    }

    private void extractMatchesAndNonMatches(ConditionAndOutcomes conditionAndOutcomes, 
                List<ConditionAndOutcome> matches, List<ConditionAndOutcome> nonMatches) {
        for (ConditionAndOutcome conditionAndOutcome : conditionAndOutcomes) {
            if (conditionAndOutcome.getOutcome().isMatch()) {
                matches.add(conditionAndOutcome);
            } else {
                nonMatches.add(conditionAndOutcome);
            }
        }
    }

    private void logNonMatches(StringBuilder message, List<ConditionAndOutcome> nonMatches) {
        message.append(String.format("      Did not match:%n"));
        for (ConditionAndOutcome nonMatch : nonMatches) {
            logConditionAndOutcome(message, "         ", nonMatch);
        }
    }

    private void logMatches(StringBuilder message, List<ConditionAndOutcome> matches) {
        if (!matches.isEmpty()) {
            message.append(String.format("      Matched:%n"));
            for (ConditionAndOutcome match : matches) {
                logConditionAndOutcome(message, "         ", match);
            }
        }
    }

    private void logConditionAndOutcome(StringBuilder message, String indent, ConditionAndOutcome conditionAndOutcome) {
        // Existing implementation of logConditionAndOutcome method
    }
}
