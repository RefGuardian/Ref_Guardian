public class test181 {

    @Test
    	void sessionCookieConfigurationIsAppliedToAutoConfiguredWebSessionIdResolver() {
    		this.contextRunner
                .withUserConfiguration(Config.class)
    			.withPropertyValues("spring.data.redis.host=" + redis.getHost(),
    					"spring.data.redis.port=" + redis.getFirstMappedPort(),
    					"server.reactive.session.cookie.name:JSESSIONID",
    					"server.reactive.session.cookie.domain:.example.com",
    					"server.reactive.session.cookie.path:/example", "server.reactive.session.cookie.max-age:60",
    					"server.reactive.session.cookie.http-only:false", "server.reactive.session.cookie.secure:false",
    					"server.reactive.session.cookie.same-site:strict")
    			.run(assertExchangeWithSession(this::verifyExchange));
    	}

    private void verifyExchange(final WebTestClient.ResponseSpec exchange) {
        List<ResponseCookie> cookies = exchange.getResponse().getCookies().get("JSESSIONID");
        assertThat(cookies).isNotEmpty();
        assertThat(cookies).allMatch((cookie) -> cookie.getDomain().equals(".example.com"));
        assertThat(cookies).allMatch((cookie) -> cookie.getPath().equals("/example"));
        assertThat(cookies).allMatch((cookie) -> cookie.getMaxAge().equals(Duration.ofSeconds(60)));
        assertThat(cookies).allMatch((cookie) -> !cookie.isHttpOnly());
        assertThat(cookies).allMatch((cookie) -> !cookie.isSecure());
        assertThat(cookies).allMatch((cookie) -> cookie.getSameSite().equals("Strict"));
    }
}
