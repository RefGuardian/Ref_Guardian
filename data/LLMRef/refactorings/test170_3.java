public class test170 {

    @Test
    void testWithCustomSettings() {
        TestConfiguration config = loadTestConfiguration();
        assertThat(this.context.getBean(RepositoryRestMvcConfiguration.class)).isNotNull();
        RepositoryRestConfiguration bean = this.context.getBean(RepositoryRestConfiguration.class);
        assertThat(bean.getDefaultPageSize()).isEqualTo(config.defaultPageSize);
        assertThat(bean.getMaxPageSize()).isEqualTo(config.maxPageSize);
        assertThat(bean.getPageParamName()).isEqualTo(config.pageParamName);
        assertThat(bean.getLimitParamName()).isEqualTo(config.limitParamName);
        assertThat(bean.getSortParamName()).isEqualTo(config.sortParamName);
        assertThat(bean.getRepositoryDetectionStrategy()).isEqualTo(RepositoryDetectionStrategies.VISIBILITY);
        assertThat(bean.getDefaultMediaType()).isEqualTo(MediaType.parseMediaType(config.defaultMediaType));
        assertThat(bean.returnBodyOnCreate(null)).isFalse();
        assertThat(bean.returnBodyOnUpdate(null)).isFalse();
        assertThat(bean.isEnableEnumTranslation()).isTrue();
    }

    private TestConfiguration loadTestConfiguration() {
        return load(TestConfiguration.class, "spring.data.rest.default-page-size:42", "spring.data.rest.max-page-size:78",
                "spring.data.rest.page-param-name:_page", "spring.data.rest.limit-param-name:_limit",
                "spring.data.rest.sort-param-name:_sort", "spring.data.rest.detection-strategy=visibility",
                "spring.data.rest.default-media-type:application/my-json",
                "spring.data.rest.return-body-on-create:false", "spring.data.rest.return-body-on-update:false",
                "spring.data.rest.enable-enum-translation:true");
    }

    private static class TestConfiguration {
        private int defaultPageSize = 42;
        private int maxPageSize = 78;
        private String pageParamName = "_page";
        private String limitParamName = "_limit";
        private String sortParamName = "_sort";
        private String defaultMediaType = "application/my-json";
    }
}
