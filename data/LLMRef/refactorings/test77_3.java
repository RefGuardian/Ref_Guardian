public class test77 {

    @Test
    void resourceHandlerChainCustomized() {
        String[] propertyValues = {
                "spring.web.resources.chain.enabled:true",
                "spring.web.resources.chain.cache:false",
                "spring.web.resources.chain.strategy.content.enabled:true",
                "spring.web.resources.chain.strategy.content.paths:/**,/*.png",
                "spring.web.resources.chain.strategy.fixed.enabled:true",
                "spring.web.resources.chain.strategy.fixed.version:test",
                "spring.web.resources.chain.strategy.fixed.paths:/**/*.js",
                "spring.web.resources.chain.html-application-cache:true",
                "spring.web.resources.chain.compressed:true"
        };
        this.contextRunner.withPropertyValues(propertyValues)
                .run((context) -> {
                    assertThat(getResourceResolvers(context, "/webjars/**")).hasSize(3);
                    assertThat(getResourceTransformers(context, "/webjars/**")).hasSize(1);
                    assertThat(getResourceResolvers(context, "/**")).extractingResultOf("getClass")
                            .containsOnly(EncodedResourceResolver.class, VersionResourceResolver.class,
                                    PathResourceResolver.class);
                    assertThat(getResourceTransformers(context, "/**")).extractingResultOf("getClass")
                            .containsOnly(CssLinkResourceTransformer.class);
                    VersionResourceResolver resolver = (VersionResourceResolver) getResourceResolvers(context, "/**")
                            .get(1);
                    Map<String, VersionStrategy> strategyMap = resolver.getStrategyMap();
                    assertThat(strategyMap.get("/*.png")).isInstanceOf(ContentVersionStrategy.class);
                    assertThat(strategyMap.get("/**/*.js")).isInstanceOf(FixedVersionStrategy.class);
                });
    }
}
