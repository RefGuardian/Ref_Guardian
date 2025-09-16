public class test246 {

    /**
     * Create a {@link RedisClusterConfiguration} if necessary.
     * @return {@literal null} if no cluster settings are set.
     */
    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        }
        RedisProperties.Cluster clusterProperties = this.properties.getCluster();
        if (this.connectionDetails.getCluster() != null) {
            RedisClusterConfiguration config = createRedisClusterConfiguration(clusterProperties);
            return config;
        }
        return null;
    }

    private RedisClusterConfiguration createRedisClusterConfiguration(RedisProperties.Cluster clusterProperties) {
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        config.setClusterNodes(getNodes(this.connectionDetails.getCluster()));
        if (clusterProperties != null && clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        config.setUsername(this.connectionDetails.getUsername());
        String password = this.connectionDetails.getPassword();
        if (password != null) {
            config.setPassword(RedisPassword.of(password));
        }
        return config;
    }
}
