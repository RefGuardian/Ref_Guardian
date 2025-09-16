public class test108 {

    @Test
    	void testDataSourcePropertiesOverridden() throws Exception {
    		setUpDataSourceConfiguration();
    		configureDataSourceProperties();
    		refreshContext();
    		org.apache.tomcat.jdbc.pool.DataSource ds = retrieveDataSourceFromContext();
    		assertDataSourceProperties(ds);
    	}
    
    private void setUpDataSourceConfiguration() {
        this.context.register(TomcatDataSourceConfiguration.class);
    }
    
    private void configureDataSourceProperties() {
        TestPropertyValues
            .of(PREFIX + "url:jdbc:h2:mem:testdb", PREFIX + "testWhileIdle:true", PREFIX + "testOnBorrow:true",
                PREFIX + "testOnReturn:true", PREFIX + "timeBetweenEvictionRunsMillis:10000",
                PREFIX + "minEvictableIdleTimeMillis:12345", PREFIX + "maxWait:1234",
                PREFIX + "jdbcInterceptors:SlowQueryReport", PREFIX + "validationInterval:9999")
            .applyTo(this.context);
    }
    
    private void refreshContext() {
        this.context.refresh();
    }
    
    private org.apache.tomcat.jdbc.pool.DataSource retrieveDataSourceFromContext() {
        return this.context.getBean(org.apache.tomcat.jdbc.pool.DataSource.class);
    }
    
    private void assertDataSourceProperties(org.apache.tomcat.jdbc.pool.DataSource ds){
        assertThat(ds.getUrl()).isEqualTo("jdbc:h2:mem:testdb");
        assertThat(ds.isTestWhileIdle()).isTrue();
        assertThat(ds.isTestOnBorrow()).isTrue();
        assertThat(ds.isTestOnReturn()).isTrue();
        assertThat(ds.getTimeBetweenEvictionRunsMillis()).isEqualTo(10000);
        assertThat(ds.getMinEvictableIdleTimeMillis()).isEqualTo(12345);
        assertThat(ds.getMaxWait()).isEqualTo(1234);
        assertThat(ds.getValidationInterval()).isEqualTo(9999L);
        assertDataSourceHasInterceptors(ds);
    }
}
