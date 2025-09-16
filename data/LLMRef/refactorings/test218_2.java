public class test218 {

    @Override
    	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    		ClassLoader classLoader = context.getClassLoader();
    		ConditionMessage matchMessage = ConditionMessage.empty();
    		List<String> onClasses = getCandidates(metadata, ConditionalOnClass.class);
    		if (onClasses != null) {
    			List<String> missing = filter(onClasses, ClassNameFilter.MISSING, classLoader);
    			if (!missing.isEmpty()) {
    				return ConditionOutcome.noMatch(createConditionMessageForNoMatchCondition(ConditionalOnClass.class, missing));
    			}
    			matchMessage = matchMessage.andCondition(ConditionalOnClass.class)
    				.found("required class", "required classes")
    				.items(Style.QUOTE, filter(onClasses, ClassNameFilter.PRESENT, classLoader));
    		}
    		List<String> onMissingClasses = getCandidates(metadata, ConditionalOnMissingClass.class);
    		if (onMissingClasses != null) {
    			List<String> present = filter(onMissingClasses, ClassNameFilter.PRESENT, classLoader);
    			if (!present.isEmpty()) {
    				return ConditionOutcome.noMatch(createConditionMessageForNoMatchCondition(ConditionalOnClass.class, present, "unwanted class", "unwanted classes"));
    			}
    			matchMessage = matchMessage.andCondition(ConditionalOnMissingClass.class)
    				.didNotFind("unwanted class", "unwanted classes")
    				.items(Style.QUOTE, filter(onMissingClasses, ClassNameFilter.MISSING, classLoader));
    		}
    		return ConditionOutcome.match(matchMessage);
    	}
    
    private ConditionMessage createConditionMessageForNoMatchCondition(Class<?> conditionClass, List<String> missing) {
        return ConditionMessage.forCondition(conditionClass)
            .didNotFind("required class", "required classes")
            .items(Style.QUOTE, missing);
    }

    private ConditionMessage createConditionMessageForNoMatchCondition(Class<?> conditionClass, List<String> present, String singularDesc, String pluralDesc) {
        return ConditionMessage.forCondition(conditionClass)
            .found(singularDesc, pluralDesc)
            .items(Style.QUOTE, present);
    }
}
