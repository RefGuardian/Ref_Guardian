public class test203 {

    private void customizeRemoteIpValve(ConfigurableTomcatWebServerFactory factory) {
        Remoteip remoteIpProperties = this.serverProperties.getTomcat().getRemoteip();
        String protocolHeader = remoteIpProperties.getProtocolHeader();
        String remoteIpHeader = remoteIpProperties.getRemoteIpHeader();
        if (shouldEnableValve(protocolHeader, remoteIpHeader)) {
            RemoteIpValve valve = createRemoteIpValve(remoteIpProperties, protocolHeader, remoteIpHeader);
            factory.addEngineValves(valve);
        }
    }

    private boolean shouldEnableValve(String protocolHeader, String remoteIpHeader) {
        return StringUtils.hasText(protocolHeader) || StringUtils.hasText(remoteIpHeader)
                || getOrDeduceUseForwardHeaders();
    }

    private RemoteIpValve createRemoteIpValve(Remoteip remoteIpProperties, String protocolHeader, String remoteIpHeader) {
        RemoteIpValve valve = new RemoteIpValve();
        valve.setProtocolHeader(StringUtils.hasLength(protocolHeader) ? protocolHeader : "X-Forwarded-Proto");
        if (StringUtils.hasLength(remoteIpHeader)) {
            valve.setRemoteIpHeader(remoteIpHeader);
        }
        valve.setTrustedProxies(remoteIpProperties.getTrustedProxies());
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
