public class test129 {

    @Test
    	void retryFailedExecutionOnNonRestartableJob() {
    		this.contextRunner.run((context) -> {
    			PlatformTransactionManager transactionManager = context.getBean(PlatformTransactionManager.class);
    			JobLauncherApplicationRunnerContext jobLauncherContext = new JobLauncherApplicationRunnerContext(context);
    			Job job = jobLauncherContext.jobBuilder()
    				.preventRestart()
    				.start(jobLauncherContext.stepBuilder().tasklet(throwingTasklet(), transactionManager).build())
    				.incrementer(new RunIdIncrementer())
    				.build();
    			jobLauncherContext.runner.execute(job, new JobParameters());
    			jobLauncherContext.runner.execute(job, new JobParameters());
    			// A failed job that is not restartable does not re-use the job params of
    			// the last execution, but creates a new job instance when running it again.
    			assertThat(jobLauncherContext.jobInstances()).hasSize(2);
    			assertThatExceptionOfType(JobRestartException.class).isThrownBy(() -> {
    				// try to re-run a failed execution
    				jobLauncherContext.runner.execute(job,
    						new JobParametersBuilder().addLong("run.id", 1L).toJobParameters());
    				fail("expected JobRestartException");
    			}).withMessageContaining("JobInstance already exists and is not restartable");
    		});
    	}
}
