public class test221 {

    private ConditionOutcome evaluateConditionalOnSingleCandidate(Spec<ConditionalOnSingleCandidate> spec,
                ConditionMessage matchMessage) {
            MatchResult matchResult = getMatchingBeans(spec);
            if (!matchResult.isAllMatched()) {
                return ConditionOutcome.noMatch(spec.message().didNotFind("any beans").atAll());
            }
            Set<String> allBeans = matchResult.getNamesOfAllMatches();
            if (allBeans.size() == 1) {
                return ConditionOutcome
                    .match(spec.message(matchMessage).found("a single bean").items(Style.QUOTE, allBeans));
            }
            Map<String, BeanDefinition> beanDefinitions = getBeanDefinitions(spec.context.getBeanFactory(), allBeans,
                    spec.getStrategy() == SearchStrategy.ALL);
            List<String> primaryBeans = getPrimaryBeans(beanDefinitions);
            if (primaryBeans.size() == 1) {
                return ConditionOutcome.match(spec.message(matchMessage)
                    .found("a single primary bean '" + primaryBeans.get(0) + "' from beans")
                    .items(Style.QUOTE, allBeans));
            }
            if (primaryBeans.size() > 1) {
                return ConditionOutcome
                    .noMatch(spec.message().found("multiple primary beans").items(Style.QUOTE, primaryBeans));
            }
            List<String> nonFallbackBeans = getNonFallbackBeans(beanDefinitions);
            if (nonFallbackBeans.size() == 1) {
                return ConditionOutcome.match(spec.message(matchMessage)
                    .found("a single non-fallback bean '" + nonFallbackBeans.get(0) + "' from beans")
                    .items(Style.QUOTE, allBeans));
            }
            return ConditionOutcome.noMatch(spec.message().found("multiple beans").items(Style.QUOTE, allBeans));
        }

    private List<String> getPrimaryBeans(Map<String, BeanDefinition> beanDefinitions) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitions.entrySet()) {
            if (entry.getValue().isPrimary()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
