public class test257 {

    private ClusterEnvironment.Builder initializeEnvironmentBuilder(CouchbaseConnectionDetails connectionDetails) {
        ClusterEnvironment.Builder builder = ClusterEnvironment.builder();
        Timeouts timeouts = this.properties.getEnv().getTimeouts();
        builder.timeoutConfig((config) -> config.kvTimeout(timeouts.getKeyValue())
                .analyticsTimeout(timeouts.getAnalytics())
                .kvDurableTimeout(timeouts.getKeyValueDurable())
                .queryTimeout(timeouts.getQuery())
                .viewTimeout(timeouts.getView())
                .searchTimeout(timeouts.getSearch())
                .managementTimeout(timeouts.getManagement())
                .connectTimeout(timeouts.getConnect())
                .disconnectTimeout(timeouts.getDisconnect());
        CouchbaseProperties.Io io = this.properties.getEnv().getIo();
        builder.ioConfig((config) -> config.maxHttpConnections(io.getMaxEndpoints())
                .numKvConnections(io.getMinEndpoints())
                .idleHttpConnectionTimeout(io.getIdleHttpConnectionTimeout());
        SslBundle sslBundle = connectionDetails.getSslBundle();
        if (sslBundle != null) {
            configureSsl(builder, sslBundle);
        }
        return builder;
    }
}
