public class test137 {

	@Test
    	void allMongoClientSettingsCanBeSet() {
    		MongoClientSettings settings = buildMongoClientSettings("test", 
    			1000, 1000, 10001, 501, 120001, 
    			60000, 60000, true);
    
    		T client = createMongoClient(settings);
    		MongoClientSettings wrapped = getClientSettings(client);
    		assertThat(wrapped.getSocketSettings().getConnectTimeout(TimeUnit.MILLISECONDS))
    			.isEqualTo(settings.getSocketSettings().getConnectTimeout(TimeUnit.MILLISECONDS));
    		assertThat(wrapped.getSocketSettings().getReadTimeout(TimeUnit.MILLISECONDS))
    			.isEqualTo(settings.getSocketSettings().getReadTimeout(TimeUnit.MILLISECONDS));
    		assertThat(wrapped.getServerSettings().getHeartbeatFrequency(TimeUnit.MILLISECONDS))
    			.isEqualTo(settings.getServerSettings().getHeartbeatFrequency(TimeUnit.MILLISECONDS));
    		assertThat(wrapped.getServerSettings().getMinHeartbeatFrequency(TimeUnit.MILLISECONDS))
    			.isEqualTo(settings.getServerSettings().getMinHeartbeatFrequency(TimeUnit.MILLISECONDS));
    		assertThat(wrapped.getApplicationName()).isEqualTo(settings.getApplicationName());
    		assertThat(wrapped.getConnectionPoolSettings().getMaxWaitTime(TimeUnit.MILLISECONDS))
    			.isEqualTo(settings.getConnectionPoolSettings().getMaxWaitTime(TimeUnit.MILLISECONDS));
    		assertThat(wrapped.getConnectionPoolSettings().getMaxConnectionLifeTime(TimeUnit.MILLISECONDS))
    			.isEqualTo(settings.getConnectionPoolSettings().getMaxConnectionLifeTime(TimeUnit.MILLISECONDS));
    		assertThat(wrapped.getConnectionPoolSettings().getMaxConnectionIdleTime(TimeUnit.MILLISECONDS))
    			.isEqualTo(settings.getConnectionPoolSettings().getMaxConnectionIdleTime(TimeUnit.MILLISECONDS));
    		assertThat(wrapped.getSslSettings().isEnabled()).isEqualTo(settings.getSslSettings().isEnabled());
    	}

	private MongoClientSettings buildMongoClientSettings(String applicationName, int connectTimeout, 
			int readTimeout, int heartbeatFrequency, int minHeartbeatFrequency, int maxWaitTime, 
			int maxConnectionLifeTime, int maxConnectionIdleTime, boolean enableSsl) {
		MongoClientSettings.Builder builder = MongoClientSettings.builder();
		builder.applyToSocketSettings((settings) -> {
			settings.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
			settings.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
		}).applyToServerSettings((settings) -> {
			settings.heartbeatFrequency(heartbeatFrequency, TimeUnit.MILLISECONDS);
			settings.minHeartbeatFrequency(minHeartbeatFrequency, TimeUnit.MILLISECONDS);
		}).applyToConnectionPoolSettings((settings) -> {
			settings.maxWaitTime(maxWaitTime, TimeUnit.MILLISECONDS);
			settings.maxConnectionLifeTime(maxConnectionLifeTime, TimeUnit.MILLISECONDS);
			settings.maxConnectionIdleTime(maxConnectionIdleTime, TimeUnit.MILLISECONDS);
		}).applyToSslSettings((settings) -> settings.enabled(enableSsl)).applicationName(applicationName);

		return builder.build();
	}
}
