public class test188 {

    private org.springframework.data.redis.cache.RedisCacheConfiguration createConfiguration(
    			CacheProperties cacheProperties, ClassLoader classLoader) {
    		Redis redisProperties = cacheProperties.getRedis();
    		org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
    			.defaultCacheConfig();
    		config = config
    			.serializeValuesWith(SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
    		if (redisProperties.getTimeToLive() != null) {
    			config = config.entryTtl(redisProperties.getTimeToLive());
    		}
    		if (redisProperties.getKeyPrefix() != null) {
    			config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
    		}
    		if (!redisProperties.isCacheNullValues()) {
    			config = config.disableCachingNullValues();
    		}
    		if (!redisProperties.isUseKeyPrefix()) {
    			config = config.disableKeyPrefix();
    		}
    		return config;
    	}
}
