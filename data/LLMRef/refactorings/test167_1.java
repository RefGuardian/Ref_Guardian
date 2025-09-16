public class test167 {

    @Test
    void customizePageable() {
        String pageParameter = "p";
        String sizeParameter = "s";
        int defaultPageSize = 10;
        String prefix = "abc";
        String qualifierDelimiter = "__";
        int maxPageSize = 100;
        PageSerializationMode serializationMode = PageSerializationMode.VIA_DTO;
        boolean oneIndexedParameters = true;

        this.contextRunner
            .withPropertyValues("spring.data.web.pageable.page-parameter=" + pageParameter,
                    "spring.data.web.pageable.size-parameter=" + sizeParameter,
                    "spring.data.web.pageable.default-page-size=" + defaultPageSize,
                    "spring.data.web.pageable.prefix=" + prefix,
                    "spring.data.web.pageable.qualifier-delimiter=" + qualifierDelimiter,
                    "spring.data.web.pageable.max-page-size=" + maxPageSize,
                    "spring.data.web.pageable.serialization-mode=" + serializationMode,
                    "spring.data.web.pageable.one-indexed-parameters=" + oneIndexedParameters)
            .run((context) -> {
                PageableHandlerMethodArgumentResolver argumentResolver = context
                    .getBean(PageableHandlerMethodArgumentResolver.class);
                SpringDataWebSettings springDataWebSettings = context.getBean(SpringDataWebSettings.class);
                assertThat(argumentResolver).hasFieldOrPropertyWithValue("pageParameterName", pageParameter);
                assertThat(argumentResolver).hasFieldOrPropertyWithValue("sizeParameterName", sizeParameter);
                assertThat(argumentResolver).hasFieldOrPropertyWithValue("oneIndexedParameters", oneIndexedParameters);
                assertThat(argumentResolver).hasFieldOrPropertyWithValue("prefix", prefix);
                assertThat(argumentResolver).hasFieldOrPropertyWithValue("qualifierDelimiter", qualifierDelimiter);
                assertThat(argumentResolver).hasFieldOrPropertyWithValue("fallbackPageable", PageRequest.of(0, defaultPageSize));
                assertThat(argumentResolver).hasFieldOrPropertyWithValue("maxPageSize", maxPageSize);
                assertThat(springDataWebSettings.pageSerializationMode()).isEqualTo(serializationMode);
            });
    }
}
