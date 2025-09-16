public class test218 {

    @Override
   	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
   		ClassLoader classLoader = context.getClassLoader();
   		ConditionMessage matchMessage = ConditionMessage.empty();
   		List<String> onClasses = extractCandidates(metadata, ConditionalOnClass.class);
   		if (onClasses != null) {
   			List<String> missing = filterCandidates(onClasses, ClassNameFilter.MISSING, classLoader);
   			if (!missing.isEmpty()) {
   				return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnClass.class)
   					.didNotFind("required class", "required classes")
   					.items(Style.QUOTE, missing));
   			}
   			matchMessage = matchMessage.andCondition(ConditionalOnClass.class)
   				.found("required class", "required classes")
   				.items(Style.QUOTE, filterCandidates(onClasses, ClassNameFilter.PRESENT, classLoader));
   		}
   		List<String> onMissingClasses = extractCandidates(metadata, ConditionalOnMissingClass.class);
   		if (onMissingClasses != null) {
   			List<String> present = filterCandidates(onMissingClasses, ClassNameFilter.PRESENT, classLoader);
   			if (!present.isEmpty()) {
   				return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnMissingClass.class)
   					.found("unwanted class", "unwanted classes")
   					.items(Style.QUOTE, present));
   			}
   			matchMessage = matchMessage.andCondition(ConditionalOnMissingClass.class)
   				.didNotFind("unwanted class", "unwanted classes")
   				.items(Style.QUOTE, filterCandidates(onMissingClasses, ClassNameFilter.MISSING, classLoader));
   		}
   		return ConditionOutcome.match(matchMessage);
   	}
}
