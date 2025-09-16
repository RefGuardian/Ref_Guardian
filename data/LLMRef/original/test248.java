public class test248 {

    @Override
    	protected FailureAnalysis analyze(Throwable rootFailure, RedisUrlSyntaxException cause) {
    		try {
    			URI uri = new URI(cause.getUrl());
    			if ("redis-sentinel".equals(uri.getScheme())) {
    				return new FailureAnalysis(getUnsupportedSchemeDescription(cause.getUrl(), uri.getScheme()),
    						"Use spring.data.redis.sentinel properties instead of spring.data.redis.url to configure Redis sentinel addresses.",
    						cause);
    			}
    			if ("redis-socket".equals(uri.getScheme())) {
    				return new FailureAnalysis(getUnsupportedSchemeDescription(cause.getUrl(), uri.getScheme()),
    						"Configure the appropriate Spring Data Redis connection beans directly instead of setting the property 'spring.data.redis.url'.",
    						cause);
    			}
    			if (!"redis".equals(uri.getScheme()) && !"rediss".equals(uri.getScheme())) {
    				return new FailureAnalysis(getUnsupportedSchemeDescription(cause.getUrl(), uri.getScheme()),
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
