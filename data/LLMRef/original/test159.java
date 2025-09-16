public class test159 {

    @Test
    	void testJmsTemplateFullCustomization() {
    		this.contextRunner.withUserConfiguration(MessageConvertersConfiguration.class)
    			.withPropertyValues("spring.jms.template.session.acknowledge-mode=client",
    					"spring.jms.template.session.transacted=true", "spring.jms.template.default-destination=testQueue",
    					"spring.jms.template.delivery-delay=500", "spring.jms.template.delivery-mode=non-persistent",
    					"spring.jms.template.priority=6", "spring.jms.template.time-to-live=6000",
    					"spring.jms.template.receive-timeout=2000")
    			.run((context) -> {
    				JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
    				assertThat(jmsTemplate.getMessageConverter()).isSameAs(context.getBean("myMessageConverter"));
    				assertThat(jmsTemplate.isPubSubDomain()).isFalse();
    				assertThat(jmsTemplate.getSessionAcknowledgeMode()).isEqualTo(Session.CLIENT_ACKNOWLEDGE);
    				assertThat(jmsTemplate.isSessionTransacted()).isTrue();
    				assertThat(jmsTemplate.getDefaultDestinationName()).isEqualTo("testQueue");
    				assertThat(jmsTemplate.getDeliveryDelay()).isEqualTo(500);
    				assertThat(jmsTemplate.getDeliveryMode()).isOne();
    				assertThat(jmsTemplate.getPriority()).isEqualTo(6);
    				assertThat(jmsTemplate.getTimeToLive()).isEqualTo(6000);
    				assertThat(jmsTemplate.isExplicitQosEnabled()).isTrue();
    				assertThat(jmsTemplate.getReceiveTimeout()).isEqualTo(2000);
    			});
    	}
}
