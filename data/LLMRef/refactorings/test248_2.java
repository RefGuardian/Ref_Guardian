public class test248 {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, RedisUrlSyntaxException cause) {
        try {
            URI uri = new URI(cause.getUrl());
            String scheme = uri.getScheme(); 

            if ("redis-sentinel".equals(scheme)) {
                return new FailureAnalysis(getUnsupportedSchemeDescription(cause.getUrl(), scheme),
                        "Use spring.data.redis.sentinel properties instead of spring.data.redis.url to configure Redis sentinel addresses.",
                        cause);
            }
            if ("redis-socket".equals(scheme)) {
                return new FailureAnalysis(getUnsupportedSchemeDescription(cause.getUrl(), scheme),
                        "Configure the appropriate Spring Data Redis connection beans directly instead of setting the property 'spring.data.redis.url'.",
                        cause);
            }
            if (!"redis".equals(scheme) && !"rediss".equals(scheme)) {
                return new FailureAnalysis(getUnsupportedSchemeDescription(cause.getUrl(), scheme),
                        "Use the scheme 'redis://' for insecure or 'rediss://' for secure Redis standalone configuration.",
                        cause);
            }
        } 
        catch (URISyntaxException ex) {
            // fall through to default description and action
        }
        return new FailureAnalysis(getDefaultDescription(cause.getUrl()),
                "Review the value of the property 'spring.data.redis.url'.", cause);
    }
}
