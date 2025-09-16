public class test170 {

    private static final int DEFAULT_PAGE_SIZE = 42;
    private static final int MAX_PAGE_SIZE = 78;
    private static final String PAGE_PARAM_NAME = "_page";
    private static final String LIMIT_PARAM_NAME = "_limit";
    private static final String SORT_PARAM_NAME = "_sort";
    private static final RepositoryDetectionStrategies DETECTION_STRATEGY = RepositoryDetectionStrategies.VISIBILITY;
    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.parseMediaType("application/my-json");

    @Test
    void testWithCustomSettings() {
        load(TestConfiguration.class, "spring.data.rest.default-page-size:" + DEFAULT_PAGE_SIZE, "spring.data.rest.max-page-size:" + MAX_PAGE_SIZE,
                "spring.data.rest.page-param-name:" + PAGE_PARAM_NAME, "spring.data.rest.limit-param-name:" + LIMIT_PARAM_NAME,
                "spring.data.rest.sort-param-name:" + SORT_PARAM_NAME, "spring.data.rest.detection-strategy=" + DETECTION_STRATEGY,
                "spring.data.rest.default-media-type:" + DEFAULT_MEDIA_TYPE,
                "spring.data.rest.return-body-on-create:false", "spring.data.rest.return-body-on-update:false",
                "spring.data.rest.enable-enum-translation:true");
        assertThat(this.context.getBean(RepositoryRestMvcConfiguration.class)).isNotNull();
        RepositoryRestConfiguration bean = this.context.getBean(RepositoryRestConfiguration.class);
        assertThat(bean.getDefaultPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
        assertThat(bean.getMaxPageSize()).isEqualTo(MAX_PAGE_SIZE);
        assertThat(bean.getPageParamName()).isEqualTo(PAGE_PARAM_NAME);
        assertThat(bean.getLimitParamName()).isEqualTo(LIMIT_PARAM_NAME);
        assertThat(bean.getSortParamName()).isEqualTo(SORT_PARAM_NAME);
        assertThat(bean.getRepositoryDetectionStrategy()).isEqualTo(DETECTION_STRATEGY);
        assertThat(bean.getDefaultMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(bean.returnBodyOnCreate(null)).isFalse();
        assertThat(bean.returnBodyOnUpdate(null)).isFalse();
        assertThat(bean.isEnableEnumTranslation()).isTrue();
    }
}
