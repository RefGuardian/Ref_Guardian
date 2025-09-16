public class test135 {

    private static final String THREAD_NAME_PREFIX = "mytest-";

    @Test
    void threadPoolTaskExecutorBuilderShouldApplyCustomSettings() {
        this.contextRunner.withPropertyValues("spring.task.execution.pool.queue-capacity=10",
                "spring.task.execution.pool.core-size=2", "spring.task.execution.pool.max-size=4",
                "spring.task.execution.pool.allow-core-thread-timeout=true", "spring.task.execution.pool.keep-alive=5s",
                "spring.task.execution.pool.shutdown.accept-tasks-after-context-close=true",
                "spring.task.execution.shutdown.await-termination=true",
                "spring.task.execution.shutdown.await-termination-period=30s",
                "spring.task.execution.thread-name-prefix=mytest-")
            .run(assertThreadPoolTaskExecutor((taskExecutor) -> {
                assertThat(taskExecutor).hasFieldOrPropertyWithValue("queueCapacity", 10);
                assertThat(taskExecutor.getCorePoolSize()).isEqualTo(2);
                assertThat(taskExecutor.getMaxPoolSize()).isEqualTo(4);
                assertThat(taskExecutor).hasFieldOrPropertyWithValue("allowCoreThreadTimeOut", true);
                assertThat(taskExecutor.getKeepAliveSeconds()).isEqualTo(5);
                assertThat(taskExecutor).hasFieldOrPropertyWithValue("acceptTasksAfterContextClose", true);
                assertThat(taskExecutor).hasFieldOrPropertyWithValue("waitForTasksToCompleteOnShutdown", true);
                assertThat(taskExecutor).hasFieldOrPropertyWithValue("awaitTerminationMillis", 30000L);
                assertThat(taskExecutor.getThreadNamePrefix()).isEqualTo(THREAD_NAME_PREFIX);
            }));
    }
}
