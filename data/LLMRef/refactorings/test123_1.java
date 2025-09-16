public class test123 {

    private static final String DEFAULT_STREAMS_CONFIG_BEAN_NAME = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME;

    @SuppressWarnings("unchecked")
    	@Test
    	void streamsWithSeveralStreamsBuilderFactoryBeans() {
    		this.contextRunner
    			.withUserConfiguration(EnableKafkaStreamsConfiguration.class,
    					TestStreamsBuilderFactoryBeanConfiguration.class)
    			.withPropertyValues("spring.application.name=my-test-app",
    					"spring.kafka.bootstrap-servers=localhost:9092,localhost:9093",
    					"spring.kafka.streams.auto-startup=false")
    			.run((context) -> {
    				Properties configs = context
    					.getBean(DEFAULT_STREAMS_CONFIG_BEAN_NAME, KafkaStreamsConfiguration.class)
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
