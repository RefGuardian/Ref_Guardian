public class test76 {

    private static final int SESSION_TIMEOUT = 123;
    private static final String SESSION_TRACKING_MODES = "cookie,url";
    private static final String SESSION_COOKIE_NAME = "testname";
    private static final String SESSION_COOKIE_DOMAIN = "testdomain";
    private static final String SESSION_COOKIE_PATH = "/testpath";
    private static final boolean SESSION_COOKIE_HTTP_ONLY = true;
    private static final boolean SESSION_COOKIE_SECURE = true;
    private static final int SESSION_COOKIE_MAX_AGE = 60;

    @Test
    	void customizeSessionProperties() {
    		Map<String, String> map = new HashMap<>();
    		map.put("server.servlet.session.timeout", String.valueOf(SESSION_TIMEOUT));
    		map.put("server.servlet.session.tracking-modes", SESSION_TRACKING_MODES);
    		map.put("server.servlet.session.cookie.name", SESSION_COOKIE_NAME);
    		map.put("server.servlet.session.cookie.domain", SESSION_COOKIE_DOMAIN);
    		map.put("server.servlet.session.cookie.path", SESSION_COOKIE_PATH);
    		map.put("server.servlet.session.cookie.http-only", String.valueOf(SESSION_COOKIE_HTTP_ONLY));
    		map.put("server.servlet.session.cookie.secure", String.valueOf(SESSION_COOKIE_SECURE));
    		map.put("server.servlet.session.cookie.max-age", String.valueOf(SESSION_COOKIE_MAX_AGE));
    		bindProperties(map);
    		ConfigurableServletWebServerFactory factory = mock(ConfigurableServletWebServerFactory.class);
    		this.customizer.customize(factory);
    		then(factory).should().setSession(assertArg((session) -> {
    			assertThat(session.getTimeout()).hasSeconds(SESSION_TIMEOUT);
    			Cookie cookie = session.getCookie();
    			assertThat(cookie.getName()).isEqualTo(SESSION_COOKIE_NAME);
    			assertThat(cookie.getDomain()).isEqualTo(SESSION_COOKIE_DOMAIN);
    			assertThat(cookie.getPath()).isEqualTo(SESSION_COOKIE_PATH);
    			assertThat(cookie.getHttpOnly()).isTrue();
    			assertThat(cookie.getMaxAge()).hasSeconds(SESSION_COOKIE_MAX_AGE);
    		}));
    	}
}
