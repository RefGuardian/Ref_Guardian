public class test244 {

    /**
     * Create a {@link JmsPoolConnectionFactory} based on the specified {@link ConnectionFactory}.
     * @param connectionFactory the connection factory to wrap
     * @return a pooled connection factory
     */
    public JmsPoolConnectionFactory createPooledConnectionFactory(ConnectionFactory connectionFactory) {
        JmsPoolConnectionFactory pooledConnectionFactory = new JmsPoolConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);

        setPoolProperties(pooledConnectionFactory);

        return pooledConnectionFactory;
    }

    private void setPoolProperties(JmsPoolConnectionFactory pooledConnectionFactory) {
        pooledConnectionFactory.setBlockIfSessionPoolIsFull(this.properties.isBlockIfFull());
        if (this.properties.getBlockIfFullTimeout() != null) {
            pooledConnectionFactory.setBlockIfSessionPoolIsFullTimeout(this.properties.getBlockIfFullTimeout().toMillis());
        }
        if (this.properties.getIdleTimeout() != null) {
            pooledConnectionFactory.setConnectionIdleTimeout((int) this.properties.getIdleTimeout().toMillis());
        }
        pooledConnectionFactory.setMaxConnections(this.properties.getMaxConnections());
        pooledConnectionFactory.setMaxSessionsPerConnection(this.properties.getMaxSessionsPerConnection());
        if (this.properties.getTimeBetweenExpirationCheck() != null) {
            pooledConnectionFactory.setConnectionCheckInterval(this.properties.getTimeBetweenExpirationCheck().toMillis());
        }
        pooledConnectionFactory.setUseAnonymousProducers(this.properties.isUseAnonymousProducers());
    }
}
