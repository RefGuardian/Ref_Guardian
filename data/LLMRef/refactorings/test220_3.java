public class test220 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionOutcome matchOutcome = ConditionOutcome.match();
        MergedAnnotations annotations = metadata.getAnnotations();
        if (annotations.isPresent(ConditionalOnBean.class)) {
            matchOutcome = evaluateConditionalOnBean(new Spec<>(context, metadata, annotations, ConditionalOnBean.class), matchOutcome.getConditionMessage());
            if (!matchOutcome.isMatch()) {
                return matchOutcome;
            }
        }
        if (metadata.isAnnotated(ConditionalOnSingleCandidate.class.getName())) {
            matchOutcome = evaluateConditionalOnSingleCandidate(new SingleCandidateSpec(context, metadata, metadata.getAnnotations()), matchOutcome.getConditionMessage());
            if (!matchOutcome.isMatch()) {
                return matchOutcome;
            }
        }
        if (metadata.isAnnotated(ConditionalOnMissingBean.class.getName())) {
            matchOutcome = evaluateConditionalOnMissingBean(new Spec<>(context, metadata, annotations, ConditionalOnMissingBean.class), matchOutcome.getConditionMessage());
            if (!matchOutcome.isMatch()) {
                return matchOutcome;
            }
        }
        return matchOutcome;
    }
}
