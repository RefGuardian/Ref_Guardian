public class test110 {

    @Test
    	void hikariAutoConfiguredWithoutDataSourceName() throws MalformedObjectNameException {
    		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    		Set<ObjectInstance> existingInstances = mBeanServer.queryMBeans(new ObjectName("com.zaxxer.hikari:type=*"),
    				null);
    		this.contextRunner
    			.withPropertyValues("spring.datasource.type=" + HikariDataSource.class.getName(),
    					"spring.datasource.hikari.register-mbeans=true")
    			.run((context) -> {
    				assertThat(context).hasSingleBean(HikariDataSource.class);
    				HikariDataSource hikariDataSource = context.getBean(HikariDataSource.class);
    				assertThat(hikariDataSource.isRegisterMbeans()).isTrue();
    				// Ensure that the pool has been initialized, triggering MBean
    				// registration
    				hikariDataSource.getConnection().close();
    				// We can't rely on the number of MBeans so we're checking that the
    				// pool and pool config MBeans were registered
    				assertThat(mBeanServer.queryMBeans(new ObjectName("com.zaxxer.hikari:type=*"), null))
    					.hasSize(existingInstances.size() + 2);
    			});
    	}

    private void extractMethodForHikariAutoConfiguration() {
        this.contextRunner
                .withPropertyValues("spring.datasource.type=" + HikariDataSource.class.getName(),
                        "spring.datasource.hikari.register-mbeans=true")
                .run((context) -> {
                    assertThat(context).hasSingleBean(HikariDataSource.class);
                    HikariDataSource hikariDataSource = context.getBean(HikariDataSource.class);
                    assertThat(hikariDataSource.isRegisterMbeans()).isTrue();
                    // Ensure that the pool has been initialized, triggering MBean
                    // registration
                    hikariDataSource.getConnection().close();
                    // We can't rely on the number of MBeans so we're checking that the
                    // pool and pool config MBeans were registered
                    assertThat(mBeanServer.queryMBeans(new ObjectName("com.zaxxer.hikari:type=*"), null))
                            .hasSize(existingInstances.size() + 2);
                });
    }

}
