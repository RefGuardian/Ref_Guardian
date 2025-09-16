public class test188 {

    private org.springframework.data.redis.cache.RedisCacheConfiguration createConfiguration(
    			CacheProperties cacheProperties, ClassLoader classLoader) {
    		Redis redisProperties = cacheProperties.getRedis();
    		org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
    			.defaultCacheConfig();
    		config = config
    			.serializeValuesWith(SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader)))
    			.entryTtl(redisProperties.getTimeToLive())
    			.prefixCacheNameWith(redisProperties.getKeyPrefix())
    			.disableCachingNullValues()
    			.disableKeyPrefix();
    		return config;
    	}
}
