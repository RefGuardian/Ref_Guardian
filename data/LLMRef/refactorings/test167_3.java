public class test167 {

    private static final String PAGE_PARAMETER = "p";
    private static final String SIZE_PARAMETER = "s";
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String PREFIX = "abc";
    private static final String QUALIFIER_DELIMITER = "__";
    private static final int MAX_PAGE_SIZE = 100;
    private static final PageSerializationMode SERIALIZATION_MODE = PageSerializationMode.VIA_DTO;

    @Test
    	void customizePageable() {
    		this.contextRunner
    			.withPropertyValues("spring.data.web.pageable.page-parameter=" + PAGE_PARAMETER,
    					"spring.data.web.pageable.size-parameter=" + SIZE_PARAMETER, "spring.data.web.pageable.default-page-size=" + DEFAULT_PAGE_SIZE,
    					"spring.data.web.pageable.prefix=" + PREFIX, "spring.data.web.pageable.qualifier-delimiter=" + QUALIFIER_DELIMITER,
    					"spring.data.web.pageable.max-page-size=" + MAX_PAGE_SIZE, "spring.data.web.pageable.serialization-mode=" + SERIALIZATION_MODE,
    					"spring.data.web.pageable.one-indexed-parameters=true")
    			.run((context) -> {
    				PageableHandlerMethodArgumentResolver argumentResolver = context
    					.getBean(PageableHandlerMethodArgumentResolver.class);
    				SpringDataWebSettings springDataWebSettings = context.getBean(SpringDataWebSettings.class);
    				assertThat(argumentResolver).hasFieldOrPropertyWithValue("pageParameterName", PAGE_PARAMETER);
    				assertThat(argumentResolver).hasFieldOrPropertyWithValue("sizeParameterName", SIZE_PARAMETER);
    				assertThat(argumentResolver).hasFieldOrPropertyWithValue("oneIndexedParameters", true);
    				assertThat(argumentResolver).hasFieldOrPropertyWithValue("prefix", PREFIX);
    				assertThat(argumentResolver).hasFieldOrPropertyWithValue("qualifierDelimiter", QUALIFIER_DELIMITER);
    				assertThat(argumentResolver).hasFieldOrPropertyWithValue("fallbackPageable", PageRequest.of(0, DEFAULT_PAGE_SIZE));
    				assertThat(argumentResolver).hasFieldOrPropertyWithValue("maxPageSize", MAX_PAGE_SIZE);
    				assertThat(springDataWebSettings.pageSerializationMode()).isEqualTo(SERIALIZATION_MODE);
    			});
    	}
}
