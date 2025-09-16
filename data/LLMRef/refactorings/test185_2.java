public class test185 {

    private static final String V1 = "V1";
    private static final String V2 = "V2";

    @Override
    	public void customize(JettyServletWebServerFactory factory) {
    		AbstractConfiguration configuration = new AbstractConfiguration(new AbstractConfiguration.Builder());
            factory.addConfigurations(configuration);
    
    			@Override
    			public void configure(WebAppContext context) throws Exception {
    				if (JettyWebSocketServerContainer.getContainer(context.getServletContext()) == null) {
    					WebSocketServerComponents.ensureWebSocketComponents(context.getServer(),
    							context.getContext().getContextHandler());
    					JettyWebSocketServerContainer.ensureContainer(context.getServletContext());
    				}
    				if (JakartaWebSocketServerContainer.getContainer(context.getServletContext()) == null) {
    					WebSocketServerComponents.ensureWebSocketComponents(context.getServer(),
    							context.getContext().getContextHandler());
    					WebSocketUpgradeFilter.ensureFilter(context.getServletContext());
    					WebSocketMappings.ensureMappings(context.getContext().getContextHandler());
    					JakartaWebSocketServerContainer.ensureContainer(context.getServletContext());
    				}
    			}
    
    		});
    	}
}
