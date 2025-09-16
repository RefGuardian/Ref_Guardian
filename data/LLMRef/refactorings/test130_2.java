public class test130 {

    private static final int COUNT = 0;

    @Bean
    Job discreteJob() {
        AbstractJob job = createAbstractJob();
        job.setJobRepository(this.jobRepository);
        return job;
    }

    private AbstractJob createAbstractJob() {
        return new AbstractJob("discreteRegisteredJob") {
            @Override
            public Collection<String> getStepNames() {
                return Collections.emptySet();
            }

            @Override
            public Step getStep(String stepName) {
                return null;
            }

            @Override
            protected void doExecute(JobExecution execution) {
                if (COUNT == 0) {
                    execution.setStatus(BatchStatus.COMPLETED);
                } else {
                    execution.setStatus(BatchStatus.FAILED);
                }
            }
        };
    }
}
