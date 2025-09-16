public class test156 {

    public static final String SPRING_ACTIVEMQ_POOL_ENABLED = "spring.activemq.pool.enabled";
    public static final String SPRING_ACTIVEMQ_POOL_BLOCKIFFULL = "spring.activemq.pool.blockIfFull";
    public static final String SPRING_ACTIVEMQ_POOL_BLOCKIFFULLTIMEOUT = "spring.activemq.pool.blockIfFullTimeout";
    public static final String SPRING_ACTIVEMQ_POOL_IDLETIMEOUT = "spring.activemq.pool.idleTimeout";
    public static final String SPRING_ACTIVEMQ_POOL_MAXCONNECTIONS = "spring.activemq.pool.maxConnections";
    public static final String SPRING_ACTIVEMQ_POOL_MAXSESSIONSPERCONNECTION = "spring.activemq.pool.maxSessionsPerConnection";
    public static final String SPRING_ACTIVEMQ_POOL_TIMEBETWEENEXPIRATIONCHECK = "spring.activemq.pool.timeBetweenExpirationCheck";
    public static final String SPRING_ACTIVEMQ_POOL_USEANONYMOUSPRODUCERS = "spring.activemq.pool.useAnonymousProducers";

    @Test
    void customPoolConnectionFactoryIsApplied() {
        this.contextRunner.withUserConfiguration(EmptyConfiguration.class)
            .withPropertyValues(SPRING_ACTIVEMQ_POOL_ENABLED, "true", SPRING_ACTIVEMQ_POOL_BLOCKIFFULL, "false",
                SPRING_ACTIVEMQ_POOL_BLOCKIFFULLTIMEOUT, "64", SPRING_ACTIVEMQ_POOL_IDLETIMEOUT, "512",
                SPRING_ACTIVEMQ_POOL_MAXCONNECTIONS, "256", SPRING_ACTIVEMQ_POOL_MAXSESSIONSPERCONNECTION, "1024",
                SPRING_ACTIVEMQ_POOL_TIMEBETWEENEXPIRATIONCHECK, "2048",
                SPRING_ACTIVEMQ_POOL_USEANONYMOUSPRODUCERS, "false")
            .run((context) -> {
                assertThat(context).hasSingleBean(ConnectionFactory.class)
                    .hasSingleBean(JmsPoolConnectionFactory.class)
                    .hasBean("jmsConnectionFactory");
                JmsPoolConnectionFactory connectionFactory = context.getBean(JmsPoolConnectionFactory.class);
                assertThat(context.getBean("jmsConnectionFactory")).isSameAs(connectionFactory);
                assertThat(connectionFactory.isBlockIfSessionPoolIsFull()).isFalse();
                assertThat(connectionFactory.getBlockIfSessionPoolIsFullTimeout()).isEqualTo(64);
                assertThat(connectionFactory.getConnectionIdleTimeout()).isEqualTo(512);
                assertThat(connectionFactory.getMaxConnections()).isEqualTo(256);
                assertThat(connectionFactory.getMaxSessionsPerConnection()).isEqualTo(1024);
                assertThat(connectionFactory.getConnectionCheckInterval()).isEqualTo(2048);
                assertThat(connectionFactory.isUseAnonymousProducers()).isFalse();
            });
    }
}
