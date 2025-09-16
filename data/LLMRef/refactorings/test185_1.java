public class test185 {

    private static final String WEB_SOCKET_SERVER_CONTAINER = "webSocketServerContainer";

    @Override
    public void customize(JettyServletWebServerFactory factory) {
        factory.addConfigurations(new AbstractConfiguration(new AbstractConfiguration.Builder()) {

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
