public class test92 {

    @Test
    	@SuppressWarnings("removal")
    	void testTomcatBinding() {
    		Map<String, String> map = new HashMap<>();
    		map.put("server.tomcat.accesslog.conditionIf", "foo");
    		map.put("server.tomcat.accesslog.conditionUnless", "bar");
    		map.put("server.tomcat.accesslog.pattern", "%h %t '%r' %s %b");
    		map.put("server.tomcat.accesslog.prefix", "foo");
    		map.put("server.tomcat.accesslog.suffix", "-bar.log");
    		map.put("server.tomcat.accesslog.encoding", "UTF-8");
    		map.put("server.tomcat.accesslog.locale", "en-AU");
    		map.put("server.tomcat.accesslog.checkExists", "true");
    		map.put("server.tomcat.accesslog.rotate", "false");
    		map.put("server.tomcat.accesslog.rename-on-rotate", "true");
    		map.put("server.tomcat.accesslog.ipv6Canonical", "true");
    		map.put("server.tomcat.accesslog.request-attributes-enabled", "true");
    		map.put("server.tomcat.remoteip.protocol-header", "X-Forwarded-Protocol");
    		map.put("server.tomcat.remoteip.remote-ip-header", "Remote-Ip");
    		map.put("server.tomcat.remoteip.internal-proxies", "10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    		map.put("server.tomcat.remoteip.trusted-proxies", "proxy1|proxy2|proxy3");
    		map.put("server.tomcat.reject-illegal-header", "false");
    		map.put("server.tomcat.background-processor-delay", "10");
    		map.put("server.tomcat.relaxed-path-chars", "|,<");
    		map.put("server.tomcat.relaxed-query-chars", "^  ,  | ");
    		map.put("server.tomcat.use-relative-redirects", "true");
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
