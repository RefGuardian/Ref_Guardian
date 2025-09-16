public class test181 {

    private static final String HOST = redis.getHost();
    private static final int PORT = redis.getFirstMappedPort();
    private static final String COOKIE_NAME = "JSESSIONID";
    private static final String COOKIE_DOMAIN = ".example.com";
    private static final String COOKIE_PATH = "/example";
    private static final int COOKIE_MAX_AGE = 60;
    private static final boolean COOKIE_HTTP_ONLY = false;
    private static final boolean COOKIE_SECURE = false;
    private static final String COOKIE_SAME_SITE = "strict";

    @Test
    void sessionCookieConfigurationIsAppliedToAutoConfiguredWebSessionIdResolver() {
        this.contextRunner.withUserConfiguration(Config.class)
            .withPropertyValues("spring.data.redis.host=" + HOST,
                "spring.data.redis.port=" + PORT,
                "server.reactive.session.cookie.name:" + COOKIE_NAME,
                "server.reactive.session.cookie.domain:" + COOKIE_DOMAIN,
                "server.reactive.session.cookie.path:" + COOKIE_PATH, "server.reactive.session.cookie.max-age:" + COOKIE_MAX_AGE,
                "server.reactive.session.cookie.http-only:" + COOKIE_HTTP_ONLY, "server.reactive.session.cookie.secure:" + COOKIE_SECURE,
                "server.reactive.session.cookie.same-site:" + COOKIE_SAME_SITE)
            .run(assertExchangeWithSession((exchange) -> {
                List<ResponseCookie> cookies = exchange.getResponse().getCookies().get(COOKIE_NAME);
                assertThat(cookies).isNotEmpty();
                assertThat(cookies).allMatch((cookie) -> cookie.getDomain().equals(COOKIE_DOMAIN));
                assertThat(cookies).allMatch((cookie) -> cookie.getPath().equals(COOKIE_PATH));
                assertThat(cookies).allMatch((cookie) -> cookie.getMaxAge().equals(Duration.ofSeconds(COOKIE_MAX_AGE)));
                assertThat(cookies).allMatch((cookie) -> !cookie.isHttpOnly());
                assertThat(cookies).allMatch((cookie) -> !cookie.isSecure());
                assertThat(cookies).allMatch((cookie) -> cookie.getSameSite().equals("Strict"));
            }));
    }
}
