public class test245 {

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        }
        if (this.connectionDetails.getSentinel() != null) {
            return createRedisSentinelConfiguration();
        }
        return null;
    }

    private RedisSentinelConfiguration createRedisSentinelConfiguration() {
        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.master(this.connectionDetails.getSentinel().getMaster());
        config.setSentinels(createSentinels(this.connectionDetails.getSentinel()));
        config.setUsername(this.connectionDetails.getUsername());
        String password = this.connectionDetails.getPassword();
        if (password != null) {
            config.setPassword(RedisPassword.of(password));
        }
        config.setSentinelUsername(this.connectionDetails.getSentinel().getUsername());
        String sentinelPassword = this.connectionDetails.getSentinel().getPassword();
        if (sentinelPassword != null) {
            config.setSentinelPassword(RedisPassword.of(sentinelPassword));
        }
        config.setDatabase(this.connectionDetails.getSentinel().getDatabase());
        return config;
    }
}
