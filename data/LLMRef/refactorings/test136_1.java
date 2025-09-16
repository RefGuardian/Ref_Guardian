public class test136 {

    @Test
    	void enableAsyncUsesAsyncConfigurerWhenModeIsForce() {
    		this.contextRunner
    			.withPropertyValues("spring.task.execution.thread-name-prefix=auto-task-",
    					"spring.task.execution.mode=force")
    			.withBean("taskExecutor", Executor.class, () -> createCustomAsyncExecutor("custom-task-"))
    			.withBean("customAsyncConfigurer", AsyncConfigurer.class, () -> getAsyncExecutor())
    			.withUserConfiguration(AsyncConfiguration.class, TestBean.class)
    			.run((context) -> {
    				assertThat(context).hasSingleBean(AsyncConfigurer.class);
    				assertThat(context.getBeansOfType(Executor.class)).hasSize(2)
    					.containsOnlyKeys("taskExecutor", "applicationTaskExecutor");
    				TestBean bean = context.getBean(TestBean.class);
    				String text = bean.echo("something").get();
    				assertThat(text).contains("async-task-").contains("something");
    			});
    	}

    private Executor getAsyncExecutor() {
		return createCustomAsyncExecutor("async-task-");
	}
}
