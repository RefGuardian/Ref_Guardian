public class test77 {

    @Test
    void resourceHandlerChainCustomized() {
        String chainEnabled = "spring.web.resources.chain.enabled:true";
        String chainCache = "spring.web.resources.chain.cache:false";
        String chainContentEnabled = "spring.web.resources.chain.strategy.content.enabled:true";
        String chainContentPaths = "spring.web.resources.chain.strategy.content.paths:/**,/*.png";
        String chainFixedEnabled = "spring.web.resources.chain.strategy.fixed.enabled:true";
        String chainFixedVersion = "spring.web.resources.chain.strategy.fixed.version:test";
        String chainFixedPaths = "spring.web.resources.chain.strategy.fixed.paths:/**/*.js";
        String htmlApplicationCache = "spring.web.resources.chain.html-application-cache:true";
        String chainCompressed = "spring.web.resources.chain.compressed:true";

        this.contextRunner.withPropertyValues(chainEnabled, chainCache, chainContentEnabled, chainContentPaths,
                chainFixedEnabled, chainFixedVersion, chainFixedPaths, htmlApplicationCache, chainCompressed)
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
