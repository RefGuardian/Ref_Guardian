public class test185 {

    private static final String JETTY_CONTAINER = JettyWebSocketServerContainer.getContainer(context.getServletContext());
    private static final String JAKARTA_CONTAINER = JakartaWebSocketServerContainer.getContainer(context.getServletContext());

    @Override
    public void customize(JettyServletWebServerFactory factory) {
        factory.addConfigurations(new AbstractConfiguration(new AbstractConfiguration.Builder()) {

            @Override
            public void configure(WebAppContext context) throws Exception {
                if (JETTY_CONTAINER == null) {
                    WebSocketServerComponents.ensureWebSocketComponents(context.getServer(),
                            context.getContext().getContextHandler());
                    JettyWebSocketServerContainer.ensureContainer(context.getServletContext());
                }
                if (JAKARTA_CONTAINER == null) {
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
