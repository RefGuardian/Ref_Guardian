public class test182 {

    @Test
    void sessionCookieConfigurationIsAppliedToAutoConfiguredWebSessionIdResolver() {
        AutoConfigurations autoConfigurations = AutoConfigurations.of(SessionAutoConfiguration.class,
                MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoReactiveAutoConfiguration.class,
                MongoReactiveDataAutoConfiguration.class, WebSessionIdResolverAutoConfiguration.class);
        new ReactiveWebApplicationContextRunner().withConfiguration(autoConfigurations)
                .withUserConfiguration(Config.class)
                .withClassLoader(new FilteredClassLoader(ReactiveRedisSessionRepository.class))
                .withPropertyValues("server.reactive.session.cookie.name:JSESSIONID",
                        "server.reactive.session.cookie.domain:.example.com",
                        "server.reactive.session.cookie.path:/example", "server.reactive.session.cookie.max-age:60",
                        "server.reactive.session.cookie.http-only:false", "server.reactive.session.cookie.secure:false",
                        "server.reactive.session.cookie.same-site:strict",
                        "spring.data.mongodb.uri=" + mongoDb.getReplicaSetUrl())
                .run(assertExchangeWithSession(this::assertSessionCookieProperties));
    }

    private void assertSessionCookieProperties(ServerWebExchange exchange) {
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
