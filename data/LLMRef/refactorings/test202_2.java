public class test202 {

    @Override
    public void customize(ConfigurableJettyWebServerFactory factory) {
        extractMethod(this, factory);
    }

    private void extractMethod(test202 test202, ConfigurableJettyWebServerFactory factory) {
        ServerProperties.Jetty properties = this.serverProperties.getJetty();
        factory.setUseForwardHeaders(getOrDeduceUseForwardHeaders());
        ServerProperties.Jetty.Threads threadProperties = properties.getThreads();
        factory.setThreadPool(JettyThreadPool.create(properties.getThreads()));
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties::getMaxConnections).to(factory::setMaxConnections);
        map.from(threadProperties::getAcceptors).to(factory::setAcceptors);
        map.from(threadProperties::getSelectors).to(factory::setSelectors);
        map.from(this.serverProperties::getMaxHttpRequestHeaderSize)
            .asInt(DataSize::toBytes)
            .when(this::isPositive)
            .to(customizeHttpConfigurations(factory, HttpConfiguration::setRequestHeaderSize));
        map.from(properties::getMaxHttpResponseHeaderSize)
            .asInt(DataSize::toBytes)
            .when(this::isPositive)
            .to(customizeHttpConfigurations(factory, HttpConfiguration::setResponseHeaderSize));
        map.from(properties::getMaxHttpFormPostSize)
            .asInt(DataSize::toBytes)
            .when(this::isPositive)
            .to(customizeServletContextHandler(factory, ServletContextHandler::setMaxFormContentSize));
        map.from(properties::getMaxFormKeys)
            .when(this::isPositive)
            .to(customizeServletContextHandler(factory, ServletContextHandler::setMaxFormKeys));
        map.from(properties::getConnectionIdleTimeout)
            .as(Duration::toMillis)
            .to(customizeAbstractConnectors(factory, AbstractConnector::setIdleTimeout));
        map.from(properties::getAccesslog)
            .when(ServerProperties.Jetty.Accesslog::isEnabled)
            .to((accesslog) -> customizeAccessLog(factory, accesslog));
    }
}
