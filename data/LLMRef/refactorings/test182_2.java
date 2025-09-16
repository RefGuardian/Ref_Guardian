public class test182 {

    private static final String SERVER_REACTIVE_SESSION_COOKIE_NAME = "server.reactive.session.cookie.name:JSESSIONID";
    private static final String SERVER_REACTIVE_SESSION_COOKIE_DOMAIN = "server.reactive.session.cookie.domain:.example.com";
    private static final String SERVER_REACTIVE_SESSION_COOKIE_PATH = "server.reactive.session.cookie.path:/example";
    private static final String SERVER_REACTIVE_SESSION_COOKIE_MAX_AGE = "server.reactive.session.cookie.max-age:60";
    private static final String SERVER_REACTIVE_SESSION_COOKIE_HTTP_ONLY = "server.reactive.session.cookie.http-only:false";
    private static final String SERVER_REACTIVE_SESSION_COOKIE_SECURE = "server.reactive.session.cookie.secure:false";
    private static final String SERVER_REACTIVE_SESSION_COOKIE_SAME_SITE = "server.reactive.session.cookie.same-site:strict";

    @Test
    	void sessionCookieConfigurationIsAppliedToAutoConfiguredWebSessionIdResolver() {
    		AutoConfigurations autoConfigurations = AutoConfigurations.of(SessionAutoConfiguration.class,
    				MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoReactiveAutoConfiguration.class,
    				MongoReactiveDataAutoConfiguration.class, WebSessionIdResolverAutoConfiguration.class);
    		new ReactiveWebApplicationContextRunner().withConfiguration(autoConfigurations)
    			.withUserConfiguration(Config.class)
    			.withClassLoader(new FilteredClassLoader(ReactiveRedisSessionRepository.class))
    			.withPropertyValues(SERVER_REACTIVE_SESSION_COOKIE_NAME,
    					SERVER_REACTIVE_SESSION_COOKIE_DOMAIN,
    					SERVER_REACTIVE_SESSION_COOKIE_PATH, SERVER_REACTIVE_SESSION_COOKIE_MAX_AGE,
    					SERVER_REACTIVE_SESSION_COOKIE_HTTP_ONLY, SERVER_REACTIVE_SESSION_COOKIE_SECURE,
    					SERVER_REACTIVE_SESSION_COOKIE_SAME_SITE,
    					"spring.data.mongodb.uri=" + mongoDb.getReplicaSetUrl())
    			.run(assertExchangeWithSession((exchange) -> {
    				List<ResponseCookie> cookies = exchange.getResponse().getCookies().get("JSESSIONID");
    				assertThat(cookies).isNotEmpty();
    				assertThat(cookies).allMatch((cookie) -> cookie.getDomain().equals(".example.com"));
    				assertThat(cookies).allMatch((cookie) -> cookie.getPath().equals("/example"));
    				assertThat(cookies).allMatch((cookie) -> cookie.getMaxAge().equals(Duration.ofSeconds(60)));
    				assertThat(cookies).allMatch((cookie) -> !cookie.isHttpOnly());
    				assertThat(cookies).allMatch((cookie) -> !cookie.isSecure());
    				assertThat(cookies).allMatch((cookie) -> cookie.getSameSite().equals("Strict"));
    			}));
    	}
}
