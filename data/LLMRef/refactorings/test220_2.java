public class test220 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionOutcome matchOutcome = ConditionOutcome.match();
        MergedAnnotations annotations = metadata.getAnnotations();
        if (annotations.isPresent(ConditionalOnBean.class)) {
            matchOutcome = extractEvaluateConditionalOnBean(context, metadata, annotations, matchOutcome);
            if (!matchOutcome.isMatch()) {
                return matchOutcome;
            }
        }
        if (metadata.isAnnotated(ConditionalOnSingleCandidate.class.getName())) {
            matchOutcome = extractEvaluateConditionalOnSingleCandidate(context, metadata, matchOutcome);
            if (!matchOutcome.isMatch()) {
                return matchOutcome;
            }
        }
        if (metadata.isAnnotated(ConditionalOnMissingBean.class.getName())) {
            matchOutcome = extractEvaluateConditionalOnMissingBean(context, metadata, annotations, matchOutcome);
            if (!matchOutcome.isMatch()) {
                return matchOutcome;
            }
        }
        return matchOutcome;
    }
    
    private ConditionOutcome extractEvaluateConditionalOnBean(ConditionContext context, AnnotatedTypeMetadata metadata, MergedAnnotations annotations, ConditionOutcome matchOutcome) {
        Spec<ConditionalOnBean> spec = new Spec<>(context, metadata, annotations, ConditionalOnBean.class);
        return evaluateConditionalOnBean(spec, matchOutcome.getConditionMessage());
    }
    
    private ConditionOutcome extractEvaluateConditionalOnSingleCandidate(ConditionContext context, AnnotatedTypeMetadata metadata, ConditionOutcome matchOutcome) {
        Spec<ConditionalOnSingleCandidate> spec = new SingleCandidateSpec(context, metadata, metadata.getAnnotations());
        return evaluateConditionalOnSingleCandidate(spec, matchOutcome.getConditionMessage());
    }
    
    private ConditionOutcome extractEvaluateConditionalOnMissingBean(ConditionContext context, AnnotatedTypeMetadata metadata, MergedAnnotations annotations, ConditionOutcome matchOutcome) {
        Spec<ConditionalOnMissingBean> spec = new Spec<>(context, metadata, annotations, ConditionalOnMissingBean.class);
        return evaluateConditionalOnMissingBean(spec, matchOutcome.getConditionMessage());
    }
}
