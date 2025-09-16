public class test168 {

    @Test
    	void defaultPageable() {
    		this.contextRunner.run((context) -> {
    			final SpringDataWebProperties.Pageable properties = new SpringDataWebProperties().getPageable();
    			final PageableHandlerMethodArgumentResolver argumentResolver = context
    				.getBean(PageableHandlerMethodArgumentResolver.class);
    			final SpringDataWebSettings springDataWebSettings = context.getBean(SpringDataWebSettings.class);
    			assertThat(argumentResolver).hasFieldOrPropertyWithValue("pageParameterName",
    					properties.getPageParameter());
    			assertThat(argumentResolver).hasFieldOrPropertyWithValue("sizeParameterName",
    					properties.getSizeParameter());
    			assertThat(argumentResolver).hasFieldOrPropertyWithValue("oneIndexedParameters",
    					properties.isOneIndexedParameters());
    			assertThat(argumentResolver).hasFieldOrPropertyWithValue("prefix", properties.getPrefix());
    			assertThat(argumentResolver).hasFieldOrPropertyWithValue("qualifierDelimiter",
    					properties.getQualifierDelimiter());
    			assertThat(argumentResolver).hasFieldOrPropertyWithValue("fallbackPageable",
    					PageRequest.of(0, properties.getDefaultPageSize()));
    			assertThat(argumentResolver).hasFieldOrPropertyWithValue("maxPageSize", properties.getMaxPageSize());
    			assertThat(springDataWebSettings.pageSerializationMode()).isEqualTo(properties.getSerializationMode());
    		});
    	}
}
