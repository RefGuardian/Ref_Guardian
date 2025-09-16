public class test76 {

    @Test
    	void customizeSessionProperties() {
    		Map<String, String> map = new HashMap<>();
    		map.put("server.servlet.session.timeout", "123");
    		map.put("server.servlet.session.tracking-modes", "cookie,url");
    		map.put("server.servlet.session.cookie.name", "testname");
    		map.put("server.servlet.session.cookie.domain", "testdomain");
    		map.put("server.servlet.session.cookie.path", "/testpath");
    		map.put("server.servlet.session.cookie.http-only", "true");
    		map.put("server.servlet.session.cookie.secure", "true");
    		map.put("server.servlet.session.cookie.max-age", "60");
    		bindProperties(map);
    		ConfigurableServletWebServerFactory factory = mock(ConfigurableServletWebServerFactory.class);
    		this.customizer.customize(factory);
    		then(factory).should().setSession(assertArg((session) -> {
    			assertThat(session.getTimeout()).hasSeconds(123);
    			Cookie cookie = session.getCookie();
    			assertThat(cookie.getName()).isEqualTo("testname");
    			assertThat(cookie.getDomain()).isEqualTo("testdomain");
    			assertThat(cookie.getPath()).isEqualTo("/testpath");
    			assertThat(cookie.getHttpOnly()).isTrue();
    			assertThat(cookie.getMaxAge()).hasSeconds(60);
    		}));
    	}
}
