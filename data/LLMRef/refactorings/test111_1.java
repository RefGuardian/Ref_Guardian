public class test111 {

    private static final String IGNORE_WARNINGS = "spring.jdbc.template.ignore-warnings";
    private static final String FETCH_SIZE = "spring.jdbc.template.fetch-size";
    private static final String QUERY_TIMEOUT = "spring.jdbc.template.query-timeout";
    private static final String MAX_ROWS = "spring.jdbc.template.max-rows";
    private static final String SKIP_RESULTS_PROCESSING = "spring.jdbc.template.skip-results-processing";
    private static final String SKIP_UNDECLARED_RESULTS = "spring.jdbc.template.skip-undeclared-results";
    private static final String RESULTS_MAP_CASE_INSENSITIVE = "spring.jdbc.template.results-map-case-insensitive";

    @Test
    	void testJdbcTemplateWithCustomProperties() {
    		this.contextRunner
    			.withPropertyValues(IGNORE_WARNINGS + ":false", FETCH_SIZE + ":100",
    					QUERY_TIMEOUT + ":60", MAX_ROWS + ":1000",
    					SKIP_RESULTS_PROCESSING + ":true",
    					SKIP_UNDECLARED_RESULTS + ":true",
    					RESULTS_MAP_CASE_INSENSITIVE + ":true")
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
