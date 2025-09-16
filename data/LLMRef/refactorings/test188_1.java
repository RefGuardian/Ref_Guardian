public class test188 {

    private static final SerializationPair<JdkSerializationRedisSerializer> SERIALIZATION_PAIR = SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader));

    private org.springframework.data.redis.cache.RedisCacheConfiguration createConfiguration(
            CacheProperties cacheProperties, ClassLoader classLoader) {
        Redis redisProperties = cacheProperties.getRedis();
        org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
                .defaultCacheConfig();
        config = config.serializeValuesWith(SERIALIZATION_PAIR);
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
