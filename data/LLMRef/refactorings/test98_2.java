public class test98 {

    @Test
    	void rsocketSupportEnabled() {
    		test98.extractMethod();
    	}
    
    private void extractMethod() {
    		this.contextRunner.withUserConfiguration(RSocketServerConfiguration.class)
    			.withConfiguration(AutoConfigurations.of(RSocketServerAutoConfiguration.class,
    					RSocketStrategiesAutoConfiguration.class, RSocketMessagingAutoConfiguration.class,
    					RSocketRequesterAutoConfiguration.class, IntegrationAutoConfiguration.class))
    			.withPropertyValues("spring.rsocket.server.port=0", "spring.integration.rsocket.client.port=0",
    					"spring.integration.rsocket.client.host=localhost",
    					"spring.integration.rsocket.server.message-mapping-enabled=true")
    			.run((context) -> {
    				assertThat(context).hasSingleBean(ClientRSocketConnector.class)
    					.hasBean("clientRSocketConnector")
    					.hasSingleBean(ServerRSocketConnector.class)
    					.hasSingleBean(ServerRSocketMessageHandler.class)
    					.hasSingleBean(RSocketMessageHandler.class);
    
    				ServerRSocketMessageHandler serverRSocketMessageHandler = context
    					.getBean(ServerRSocketMessageHandler.class);
    				assertThat(context).getBean(RSocketMessageHandler.class).isSameAs(serverRSocketMessageHandler);
    
    				ClientRSocketConnector clientRSocketConnector = context.getBean(ClientRSocketConnector.class);
    				ClientTransport clientTransport = (ClientTransport) new DirectFieldAccessor(clientRSocketConnector)
    					.getPropertyValue("clientTransport");
    
    				assertThat(clientTransport).isInstanceOf(TcpClientTransport.class);
    			});
    }
}
