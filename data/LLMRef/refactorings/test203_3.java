public class test203 {

    private void customizeRemoteIpValve(ConfigurableTomcatWebServerFactory factory) {
        Remoteip remoteIpProperties = this.serverProperties.getTomcat().getRemoteip();
        String protocolHeader = remoteIpProperties.getProtocolHeader();
        String remoteIpHeader = remoteIpProperties.getRemoteIpHeader();
        // For back compatibility the valve is also enabled if protocol-header is set
        if (isProtocolOrRemoteIpHeaderSet(protocolHeader, remoteIpHeader) || getOrDeduceUseForwardHeaders()) {
            RemoteIpValve valve = createAndConfigureRemoteIpValve(protocolHeader, remoteIpHeader, remoteIpProperties);
            factory.addEngineValves(valve);
        }
    }

    private boolean isProtocolOrRemoteIpHeaderSet(String protocolHeader, String remoteIpHeader) {
        return StringUtils.hasText(protocolHeader) || StringUtils.hasText(remoteIpHeader);
    }

    private RemoteIpValve createAndConfigureRemoteIpValve(String protocolHeader, String remoteIpHeader, Remoteip remoteIpProperties) {
        RemoteIpValve valve = new RemoteIpValve();
        valve.setProtocolHeader(StringUtils.hasLength(protocolHeader) ? protocolHeader : "X-Forwarded-Proto");
        if (StringUtils.hasLength(remoteIpHeader)) {
            valve.setRemoteIpHeader(remoteIpHeader);
        }
        valve.setTrustedProxies(remoteIpProperties.getTrustedProxies());
        // The internal proxies default to a list of "safe" internal IP addresses
        valve.setInternalProxies(remoteIpProperties.getInternalProxies());
        try {
            valve.setHostHeader(remoteIpProperties.getHostHeader());
        } catch (NoSuchMethodError ex) {
            // Avoid failure with war deployments to Tomcat 8.5 before 8.5.44 and
            // Tomcat 9 before 9.0.23
        }
        valve.setPortHeader(remoteIpProperties.getPortHeader());
        valve.setProtocolHeaderHttpsValue(remoteIpProperties.getProtocolHeaderHttpsValue());
        return valve;
    }
}
