public class test105 {

    @Test
    	void testDirectRabbitListenerContainerFactoryWithCustomSettings() {
    		this.contextRunner
    			.withUserConfiguration(MessageConvertersConfiguration.class, MessageRecoverersConfiguration.class)
    			.withPropertyValues("spring.rabbitmq.listener.type:direct",
    					"spring.rabbitmq.listener.direct.retry.enabled:true",
    					"spring.rabbitmq.listener.direct.retry.max-attempts:4",
    					"spring.rabbitmq.listener.direct.retry.initial-interval:2000",
    					"spring.rabbitmq.listener.direct.retry.multiplier:1.5",
    					"spring.rabbitmq.listener.direct.retry.max-interval:5000",
    					"spring.rabbitmq.listener.direct.auto-startup:false",
    					"spring.rabbitmq.listener.direct.acknowledge-mode:manual",
    					"spring.rabbitmq.listener.direct.consumers-per-queue:5",
    					"spring.rabbitmq.listener.direct.prefetch:40",
    					"spring.rabbitmq.listener.direct.default-requeue-rejected:false",
    					"spring.rabbitmq.listener.direct.idle-event-interval:5",
    					"spring.rabbitmq.listener.direct.missing-queues-fatal:true",
    					"spring.rabbitmq.listener.direct.force-stop:true",
    					"spring.rabbitmq.listener.direct.observation-enabled:true")
    			.run((context) -> {
    				DirectRabbitListenerContainerFactory rabbitListenerContainerFactory = context
    					.getBean("rabbitListenerContainerFactory", DirectRabbitListenerContainerFactory.class);
    				assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("consumersPerQueue", 5);
    				assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("missingQueuesFatal", true);
    				assertThat(rabbitListenerContainerFactory).hasFieldOrPropertyWithValue("observationEnabled", true);
    				checkCommonProps(context, rabbitListenerContainerFactory);
    			});
    	}
}
