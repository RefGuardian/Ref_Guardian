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
            if (!checkIfCacheTypeIsBound(environment)) {
                return ConditionOutcome.match(message.because("automatic cache type"));
            }
            CacheType required = CacheConfigurations.getType(((AnnotationMetadata) metadata).getClassName());
            if (getSpecifiedCacheType(environment) == required) {
                return ConditionOutcome.match(message.because(getSpecifiedCacheType(environment) + " cache type"));
            }
        } catch (BindException ex) {
            // Ignore
        }
        return ConditionOutcome.noMatch(message.because("unknown cache type"));
    }

    private CacheType getSpecifiedCacheType(Environment environment) throws BindException {
        BindResult<CacheType> specified = Binder.get(environment).bind("spring.cache.type", CacheType.class);
        return specified.get();
    }

    private boolean checkIfCacheTypeIsBound(Environment environment) throws BindException {
        BindResult<CacheType> specified = Binder.get(environment).bind("spring.cache.type", CacheType.class);
        return specified.isBound();
    }
}
