public class test228 {

    public Map<String, Object> buildProperties(SslBundles sslBundles) {
    			Properties properties = new Properties();
    			PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
    			map.from(this::getAutoCommitInterval)
    				.asInt(Duration::toMillis)
    				.to(properties.in(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG));
    			map.from(this::getAutoOffsetReset).to(properties.in(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
    			map.from(this::getBootstrapServers).to(properties.in(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    			map.from(this::getClientId).to(properties.in(ConsumerConfig.CLIENT_ID_CONFIG));
    			map.from(this::getEnableAutoCommit).to(properties.in(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG));
    			map.from(this::getFetchMaxWait)
    				.asInt(Duration::toMillis)
    				.to(properties.in(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG));
    			map.from(this::getFetchMinSize)
    				.asInt(DataSize::toBytes)
    				.to(properties.in(ConsumerConfig.FETCH_MIN_BYTES_CONFIG));
    			map.from(this::getGroupId).to(properties.in(ConsumerConfig.GROUP_ID_CONFIG));
    			map.from(this::getHeartbeatInterval)
    				.asInt(Duration::toMillis)
    				.to(properties.in(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG));
    			map.from(() -> getIsolationLevel().name().toLowerCase(Locale.ROOT))
    				.to(properties.in(ConsumerConfig.ISOLATION_LEVEL_CONFIG));
    			map.from(this::getKeyDeserializer).to(properties.in(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
    			map.from(this::getValueDeserializer).to(properties.in(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
    			map.from(this::getMaxPollRecords).to(properties.in(ConsumerConfig.MAX_POLL_RECORDS_CONFIG));
    			map.from(this::getMaxPollInterval)
    				.asInt(Duration::toMillis)
    				.to(properties.in(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG));
    			return properties.with(this.ssl, this.security, this.properties, sslBundles);
    		}
}
