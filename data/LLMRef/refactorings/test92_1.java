public class test92 {

    private static final String SERVER_TOMCAT_ACCESSLOG_CONDITION_IF = "server.tomcat.accesslog.conditionIf";
    private static final String SERVER_TOMCAT_ACCESSLOG_CONDITION_UNLESS = "server.tomcat.accesslog.conditionUnless";
    private static final String SERVER_TOMCAT_ACCESSLOG_PATTERN = "server.tomcat.accesslog.pattern";
    private static final String SERVER_TOMCAT_ACCESSLOG_PREFIX = "server.tomcat.accesslog.prefix";
    private static final String SERVER_TOMCAT_ACCESSLOG_SUFFIX = "server.tomcat.accesslog.suffix";
    private static final String SERVER_TOMCAT_ACCESSLOG_ENCODING = "server.tomcat.accesslog.encoding";
    private static final String SERVER_TOMCAT_ACCESSLOG_LOCALE = "server.tomcat.accesslog.locale";
    private static final String SERVER_TOMCAT_ACCESSLOG_CHECK_EXISTS = "server.tomcat.accesslog.checkExists";
    private static final String SERVER_TOMCAT_ACCESSLOG_ROTATE = "server.tomcat.accesslog.rotate";
    private static final String SERVER_TOMCAT_ACCESSLOG_RENAME_ON_ROTATE = "server.tomcat.accesslog.rename-on-rotate";
    private static final String SERVER_TOMCAT_ACCESSLOG_IPV6_CANONICAL = "server.tomcat.accesslog.ipv6Canonical";
    private static final String SERVER_TOMCAT_ACCESSLOG_REQUEST_ATTRIBUTES_ENABLED = "server.tomcat.accesslog.request-attributes-enabled";
    private static final String SERVER_TOMCAT_REMOTEIP_PROTOCOL_HEADER = "server.tomcat.remoteip.protocol-header";
    private static final String SERVER_TOMCAT_REMOTEIP_REMOTE_IP_HEADER = "server.tomcat.remoteip.remote-ip-header";
    private static final String SERVER_TOMCAT_REMOTEIP_INTERNAL_PROXIES = "server.tomcat.remoteip.internal-proxies";
    private static final String SERVER_TOMCAT_REMOTEIP_TRUSTED_PROXIES = "server.tomcat.remoteip.trusted-proxies";
    private static final String SERVER_TOMCAT_REJECT_ILLEGAL_HEADER = "server.tomcat.reject-illegal-header";
    private static final String SERVER_TOMCAT_BACKGROUND_PROCESSOR_DELAY = "server.tomcat.background-processor-delay";
    private static final String SERVER_TOMCAT_RELAXED_PATH_CHARS = "server.tomcat.relaxed-path-chars";
    private static final String SERVER_TOMCAT_RELAXED_QUERY_CHARS = "server.tomcat.relaxed-query-chars";
    private static final String SERVER_TOMCAT_USE_RELATIVE_REDIRECTS = "server.tomcat.use-relative-redirects";

    @Test
    @SuppressWarnings("removal")
    void testTomcatBinding() {
        Map<String, String> map = new HashMap<>();
        map.put(SERVER_TOMCAT_ACCESSLOG_CONDITION_IF, "foo");
        map.put(SERVER_TOMCAT_ACCESSLOG_CONDITION_UNLESS, "bar");
        map.put(SERVER_TOMCAT_ACCESSLOG_PATTERN, "%h %t '%r' %s %b");
        map.put(SERVER_TOMCAT_ACCESSLOG_PREFIX, "foo");
        map.put(SERVER_TOMCAT_ACCESSLOG_SUFFIX, "-bar.log");
        map.put(SERVER_TOMCAT_ACCESSLOG_ENCODING, "UTF-8");
        map.put(SERVER_TOMCAT_ACCESSLOG_LOCALE, "en-AU");
        map.put(SERVER_TOMCAT_ACCESSLOG_CHECK_EXISTS, "true");
        map.put(SERVER_TOMCAT_ACCESSLOG_ROTATE, "false");
        map.put(SERVER_TOMCAT_ACCESSLOG_RENAME_ON_ROTATE, "true");
        map.put(SERVER_TOMCAT_ACCESSLOG_IPV6_CANONICAL, "true");
        map.put(SERVER_TOMCAT_ACCESSLOG_REQUEST_ATTRIBUTES_ENABLED, "true");
        map.put(SERVER_TOMCAT_REMOTEIP_PROTOCOL_HEADER, "X-Forwarded-Protocol");
        map.put(SERVER_TOMCAT_REMOTEIP_REMOTE_IP_HEADER, "Remote-Ip");
        map.put(SERVER_TOMCAT_REMOTEIP_INTERNAL_PROXIES, "10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        map.put(SERVER_TOMCAT_REMOTEIP_TRUSTED_PROXIES, "proxy1|proxy2|proxy3");
        map.put(SERVER_TOMCAT_REJECT_ILLEGAL_HEADER, "false");
        map.put(SERVER_TOMCAT_BACKGROUND_PROCESSOR_DELAY, "10");
        map.put(SERVER_TOMCAT_RELAXED_PATH_CHARS, "|,<");
        map.put(SERVER_TOMCAT_RELAXED_QUERY_CHARS, "^  ,  | ");
        map.put(SERVER_TOMCAT_USE_RELATIVE_REDIRECTS, "true");
        bind(map);
        ServerProperties.Tomcat tomcat = this.properties.getTomcat();
        Accesslog accesslog = tomcat.getAccesslog();
        assertThat(accesslog.getConditionIf()).isEqualTo("foo");
        assertThat(accesslog.getConditionUnless()).isEqualTo("bar");
        assertThat(accesslog.getPattern()).isEqualTo("%h %t '%r' %s %b");
        assertThat(accesslog.getPrefix()).isEqualTo("foo");
        assertThat(accesslog.getSuffix()).isEqualTo("-bar.log");
        assertThat(accesslog.getEncoding()).isEqualTo("UTF-8");
        assertThat(accesslog.getLocale()).isEqualTo("en-AU");
        assertThat(accesslog.isCheckExists()).isTrue();
        assertThat(accesslog.isRotate()).isFalse();
        assertThat(accesslog.isRenameOnRotate()).isTrue();
        assertThat(accesslog.isIpv6Canonical()).isTrue();
        assertThat(accesslog.isRequestAttributesEnabled()).isTrue();
        assertThat(tomcat.getRemoteip().getRemoteIpHeader()).isEqualTo("Remote-Ip");
        assertThat(tomcat.getRemoteip().getProtocolHeader()).isEqualTo("X-Forwarded-Protocol");
        assertThat(tomcat.getRemoteip().getInternalProxies()).isEqualTo("10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        assertThat(tomcat.getRemoteip().getTrustedProxies()).isEqualTo("proxy1|proxy2|proxy3");
        assertThat(tomcat.getBackgroundProcessorDelay()).hasSeconds(10);
        assertThat(tomcat.getRelaxedPathChars()).containsExactly('|', '<');
        assertThat(tomcat.getRelaxedQueryChars()).containsExactly('^', '|');
        assertThat(tomcat.isUseRelativeRedirects()).isTrue();
    }
}
