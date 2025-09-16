public class test220 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionOutcome matchOutcome = ConditionOutcome.match();
        MergedAnnotations annotations = metadata.getAnnotations();
        if (annotations.isPresent(ConditionalOnBean.class)) {
            ConditionOutcome updatedMatchOutcome = analyzeBean(metadata, context, matchOutcome);
            if (!updatedMatchOutcome.isMatch()) {
                return updatedMatchOutcome;
            }
        }
        if (metadata.isAnnotated(ConditionalOnSingleCandidate.class.getName())) {
            ConditionOutcome updatedMatchOutcome = analyzeSingleCandidate(metadata, context, matchOutcome);
            if (!updatedMatchOutcome.isMatch()) {
                return updatedMatchOutcome;
            }
        }
        if (metadata.isAnnotated(ConditionalOnMissingBean.class.getName())) {
            ConditionOutcome updatedMatchOutcome = analyzeMissingBean(metadata, context, matchOutcome);
            if (!updatedMatchOutcome.isMatch()) {
                return updatedMatchOutcome;
            }
        }
        return matchOutcome;
    }

    private ConditionOutcome analyzeBean(AnnotatedTypeMetadata metadata, ConditionContext context, ConditionOutcome matchOutcome) {
        Spec<ConditionalOnBean> spec = new Spec<>(context, metadata, metadata.getAnnotations(), ConditionalOnBean.class);
        return evaluateConditionalOnBean(spec, matchOutcome.getConditionMessage());
    }

    private ConditionOutcome analyzeSingleCandidate(AnnotatedTypeMetadata metadata, ConditionContext context, ConditionOutcome matchOutcome) {
        Spec<ConditionalOnSingleCandidate> spec = new SingleCandidateSpec(context, metadata, metadata.getAnnotations());
        return evaluateConditionalOnSingleCandidate(spec, matchOutcome.getConditionMessage());
    }

    private ConditionOutcome analyzeMissingBean(AnnotatedTypeMetadata metadata, ConditionContext context, ConditionOutcome matchOutcome) {
        Spec<ConditionalOnMissingBean> spec = new Spec<>(context, metadata, metadata.getAnnotations(), ConditionalOnMissingBean.class);
        return evaluateConditionalOnMissingBean(spec, matchOutcome.getConditionMessage());
    }
}
