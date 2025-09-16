public class test248 {

    @Override
    	protected FailureAnalysis analyze(Throwable rootFailure, RedisUrlSyntaxException cause) {
    		try {
    			URI uri = new URI(cause.getUrl());
    			if ("redis-sentinel".equals(uri.getScheme())) {
    				return getFailureAnalysis(cause, uri);
    			}
    			if ("redis-socket".equals(uri.getScheme())) {
    				return getFailureAnalysis(cause, uri);
    			}
    			if (!"redis".equals(uri.getScheme()) && !"rediss".equals(uri.getScheme())) {
    				return getFailureAnalysis(cause, uri);
    			}
    		}
    		catch (URISyntaxException ex) {
    			// fall through to default description and action
    		}
    		return new FailureAnalysis(getDefaultDescription(cause.getUrl()),
    				"Review the value of the property 'spring.data.redis.url'.", cause);
    	}

    	private FailureAnalysis getFailureAnalysis(RedisUrlSyntaxException cause, URI uri) {
    		return new FailureAnalysis(getUnsupportedSchemeDescription(cause.getUrl(), uri.getScheme()),
    						"Use spring.data.redis.sentinel properties instead of spring.data.redis.url to configure Redis sentinel addresses.",
    						cause);
    	}
}
