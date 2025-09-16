public class test123 {

    private static final String SPRING_APP_NAME = "my-test-app";
    private static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093";
    private static final String SPRING_KAFKA_STREAMS_AUTO_STARTUP = "false";

    @SuppressWarnings("unchecked")
    @Test
    void streamsWithSeveralStreamsBuilderFactoryBeans() {
        this.contextRunner
            .withUserConfiguration(EnableKafkaStreamsConfiguration.class,
                    TestStreamsBuilderFactoryBeanConfiguration.class)
            .withPropertyValues("spring.application.name=" + SPRING_APP_NAME,
                    "spring.kafka.bootstrap-servers=" + KAFKA_BOOTSTRAP_SERVERS,
                    "spring.kafka.streams.auto-startup=" + SPRING_KAFKA_STREAMS_AUTO_STARTUP)
            .run((context) -> {
                Properties configs = context
                    .getBean(KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME,
                            KafkaStreamsConfiguration.class)
                    .asProperties();
                assertThat((List<String>) configs.get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG))
                    .containsExactly("localhost:9092", "localhost:9093");
                then(context.getBean("&firstStreamsBuilderFactoryBean", StreamsBuilderFactoryBean.class))
                    .should(never())
                    .setAutoStartup(false);
                then(context.getBean("&secondStreamsBuilderFactoryBean", StreamsBuilderFactoryBean.class))
                    .should(never())
                    .setAutoStartup(false);
            });
    }
}
