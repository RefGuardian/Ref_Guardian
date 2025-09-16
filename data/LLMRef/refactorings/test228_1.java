public class test228 {
    
    private static final String AUTO_COMMIT_INTERVAL_MS_CONFIG = ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG;
    private static final String AUTO_OFFSET_RESET_CONFIG = ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
    private static final String BOOTSTRAP_SERVERS_CONFIG = ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
    private static final String CLIENT_ID_CONFIG = ConsumerConfig.CLIENT_ID_CONFIG;
    private static final String ENABLE_AUTO_COMMIT_CONFIG = ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
    private static final String FETCH_MAX_WAIT_MS_CONFIG = ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG;
    private static final String FETCH_MIN_BYTES_CONFIG = ConsumerConfig.FETCH_MIN_BYTES_CONFIG;
    private static final String GROUP_ID_CONFIG = ConsumerConfig.GROUP_ID_CONFIG;
    private static final String HEARTBEAT_INTERVAL_MS_CONFIG = ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG;
    private static final String ISOLATION_LEVEL_CONFIG = ConsumerConfig.ISOLATION_LEVEL_CONFIG;
    private static final String KEY_DESERIALIZER_CLASS_CONFIG = ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
    private static final String VALUE_DESERIALIZER_CLASS_CONFIG = ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;
    private static final String MAX_POLL_RECORDS_CONFIG = ConsumerConfig.MAX_POLL_RECORDS_CONFIG;
    private static final String MAX_POLL_INTERVAL_MS_CONFIG = ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG;
    
    public Map<String, Object> buildProperties(SslBundles sslBundles) {
        Properties properties = new Properties();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this::getAutoCommitInterval)
            .asInt(Duration::toMillis)
            .to(properties.in(AUTO_COMMIT_INTERVAL_MS_CONFIG));
        map.from(this::getAutoOffsetReset).to(properties.in(AUTO_OFFSET_RESET_CONFIG));
        map.from(this::getBootstrapServers).to(properties.in(BOOTSTRAP_SERVERS_CONFIG));
        map.from(this::getClientId).to(properties.in(CLIENT_ID_CONFIG));
        map.from(this::getEnableAutoCommit).to(properties.in(ENABLE_AUTO_COMMIT_CONFIG));
        map.from(this::getFetchMaxWait)
            .asInt(Duration::toMillis)
            .to(properties.in(FETCH_MAX_WAIT_MS_CONFIG));
        map.from(this::getFetchMinSize)
            .asInt(DataSize::toBytes)
            .to(properties.in(FETCH_MIN_BYTES_CONFIG));
        map.from(this::getGroupId).to(properties.in(GROUP_ID_CONFIG));
        map.from(this::getHeartbeatInterval)
            .asInt(Duration::toMillis)
            .to(properties.in(HEARTBEAT_INTERVAL_MS_CONFIG));
        map.from(() -> getIsolationLevel().name().toLowerCase(Locale.ROOT))
            .to(properties.in(ISOLATION_LEVEL_CONFIG));
        map.from(this::getKeyDeserializer).to(properties.in(KEY_DESERIALIZER_CLASS_CONFIG));
        map.from(this::getValueDeserializer).to(properties.in(VALUE_DESERIALIZER_CLASS_CONFIG));
        map.from(this::getMaxPollRecords).to(properties.in(MAX_POLL_RECORDS_CONFIG));
        map.from(this::getMaxPollInterval)
            .asInt(Duration::toMillis)
            .to(properties.in(MAX_POLL_INTERVAL_MS_CONFIG));
        return properties.with(this.ssl, this.security, this.properties, sslBundles);
    }
}
