public class test111 {

    @Test
    	void testJdbcTemplateWithCustomProperties() {
    		this.contextRunner
    			.withPropertyValues("spring.jdbc.template.ignore-warnings:false", "spring.jdbc.template.fetch-size:100",
    					"spring.jdbc.template.query-timeout:60", "spring.jdbc.template.max-rows:1000",
    					"spring.jdbc.template.skip-results-processing:true",
    					"spring.jdbc.template.skip-undeclared-results:true",
    					"spring.jdbc.template.results-map-case-insensitive:true")
    			.run((context) -> {
    				assertThat(context).hasSingleBean(JdbcOperations.class);
    				JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
    				assertThat(jdbcTemplate.getDataSource()).isNotNull();
    				assertThat(jdbcTemplate.isIgnoreWarnings()).isEqualTo(false);
    				assertThat(jdbcTemplate.getFetchSize()).isEqualTo(100);
    				assertThat(jdbcTemplate.getQueryTimeout()).isEqualTo(60);
    				assertThat(jdbcTemplate.getMaxRows()).isEqualTo(1000);
    				assertThat(jdbcTemplate.isSkipResultsProcessing()).isEqualTo(true);
    				assertThat(jdbcTemplate.isSkipUndeclaredResults()).isEqualTo(true);
    				assertThat(jdbcTemplate.isResultsMapCaseInsensitive()).isEqualTo(true);
    			});
    	}
}
