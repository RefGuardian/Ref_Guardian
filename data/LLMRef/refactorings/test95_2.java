public class test95 {

    private static final String REMOTE_IP_HEADER = "x-my-remote-ip-header";
    private static final String PROTOCOL_HEADER = "x-my-protocol-header";
    private static final String INTERNAL_PROXIES = "192.168.0.1";
    private static final String HOST_HEADER = "x-my-forward-host";
    private static final String PORT_HEADER = "x-my-forward-port";
    private static final String PROTOCOL_HEADER_HTTPS_VALUE = "On";
    private static final String TRUSTED_PROXIES = "proxy1|proxy2";

    @Test
    void customRemoteIpValve() {
        bind("server.tomcat.remoteip.remote-ip-header=" + REMOTE_IP_HEADER,
                "server.tomcat.remoteip.protocol-header=" + PROTOCOL_HEADER,
                "server.tomcat.remoteip.internal-proxies=" + INTERNAL_PROXIES,
                "server.tomcat.remoteip.host-header=" + HOST_HEADER,
                "server.tomcat.remoteip.port-header=" + PORT_HEADER,
                "server.tomcat.remoteip.protocol-header-https-value=" + PROTOCOL_HEADER_HTTPS_VALUE,
                "server.tomcat.remoteip.trusted-proxies=" + TRUSTED_PROXIES);
        TomcatServletWebServerFactory factory = customizeAndGetFactory();
        assertThat(factory.getEngineValves()).hasSize(1);
        Valve valve = factory.getEngineValves().iterator().next();
        assertThat(valve).isInstanceOf(RemoteIpValve.class);
        RemoteIpValve remoteIpValve = (RemoteIpValve) valve;
        assertThat(remoteIpValve.getProtocolHeader()).isEqualTo(PROTOCOL_HEADER);
        assertThat(remoteIpValve.getProtocolHeaderHttpsValue()).isEqualTo(PROTOCOL_HEADER_HTTPS_VALUE);
        assertThat(remoteIpValve.getRemoteIpHeader()).isEqualTo(REMOTE_IP_HEADER);
        assertThat(remoteIpValve.getHostHeader()).isEqualTo(HOST_HEADER);
        assertThat(remoteIpValve.getPortHeader()).isEqualTo(PORT_HEADER);
        assertThat(remoteIpValve.getInternalProxies()).isEqualTo(INTERNAL_PROXIES);
        assertThat(remoteIpValve.getTrustedProxies()).isEqualTo(TRUSTED_PROXIES);
    }
}
