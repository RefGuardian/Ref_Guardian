public class test220 {

    @Override
    	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    		ConditionOutcome matchOutcome = ConditionOutcome.match();
    		MergedAnnotations annotations = metadata.getAnnotations();
    		if (annotations.isPresent(ConditionalOnBean.class)) {
    			Spec<ConditionalOnBean> spec = new Spec<>(context, metadata, annotations, ConditionalOnBean.class);
    			matchOutcome = evaluateConditionalOnBean(spec, matchOutcome.getConditionMessage());
    			if (!matchOutcome.isMatch()) {
    				return matchOutcome;
    			}
    		}
    		if (metadata.isAnnotated(ConditionalOnSingleCandidate.class.getName())) {
    			Spec<ConditionalOnSingleCandidate> spec = new SingleCandidateSpec(context, metadata,
    					metadata.getAnnotations());
    			matchOutcome = evaluateConditionalOnSingleCandidate(spec, matchOutcome.getConditionMessage());
    			if (!matchOutcome.isMatch()) {
    				return matchOutcome;
    			}
    		}
    		if (metadata.isAnnotated(ConditionalOnMissingBean.class.getName())) {
    			Spec<ConditionalOnMissingBean> spec = new Spec<>(context, metadata, annotations,
    					ConditionalOnMissingBean.class);
    			matchOutcome = evaluateConditionalOnMissingBean(spec, matchOutcome.getConditionMessage());
    			if (!matchOutcome.isMatch()) {
    				return matchOutcome;
    			}
    		}
    		return matchOutcome;
    	}
}
