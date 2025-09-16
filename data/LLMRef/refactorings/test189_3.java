public class test189 {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String sourceClass = "";
        if (metadata instanceof ClassMetadata classMetadata) {
            sourceClass = classMetadata.getClassName();
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition("Cache", sourceClass);
        Environment environment = context.getEnvironment();
        try {
            BindResult<CacheType> specified = Binder.get(environment).bind("spring.cache.type", CacheType.class);
            if (!specified.isBound()) {
                return ConditionOutcome.match(message.because("automatic cache type"));
            }
            CacheType required = CacheConfigurations.getType(((AnnotationMetadata) metadata).getClassName());
            if (specified.get() == required) {
                return ConditionOutcome.match(message.because(specified.get() + " cache type"));
            }
        } catch (BindException ex) {
            // Ignore
        }
        return ConditionOutcome.noMatch(message.because("unknown cache type"));
    }
}
