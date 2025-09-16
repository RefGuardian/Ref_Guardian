public class test137 {

    @Test
    	void allMongoClientSettingsCanBeSet() {
    		MongoClientSettings.Builder builder = MongoClientSettings.builder();
    		builder.applyToSocketSettings((settings) -> {
    			settings.connectTimeout(1000, TimeUnit.MILLISECONDS);
    			settings.readTimeout(1000, TimeUnit.MILLISECONDS);
    		}).applyToServerSettings((settings) -> {
    			settings.heartbeatFrequency(10001, TimeUnit.MILLISECONDS);
    			settings.minHeartbeatFrequency(501, TimeUnit.MILLISECONDS);
    		}).applyToConnectionPoolSettings((settings) -> {
    			settings.maxWaitTime(120001, TimeUnit.MILLISECONDS);
    			settings.maxConnectionLifeTime(60000, TimeUnit.MILLISECONDS);
    			settings.maxConnectionIdleTime(60000, TimeUnit.MILLISECONDS);
    		}).applyToSslSettings((settings) -> settings.enabled(true)).applicationName("test");
    
    		MongoClientSettings settings = builder.build();
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
}
