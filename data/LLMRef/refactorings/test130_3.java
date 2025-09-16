public class test130 {

    private static final int COUNT_THRESHOLD = 0;

    @Bean
    Job discreteJob() {
        AbstractJob job = createDiscreteRegisteredJob();
        job.setJobRepository(this.jobRepository);
        return job;
    }

    private AbstractJob createDiscreteRegisteredJob() {
        return new AbstractJob("discreteRegisteredJob") {

            private static int count = 0;

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
                if (count <= COUNT_THRESHOLD) {
                    execution.setStatus(BatchStatus.COMPLETED);
                } else {
                    execution.setStatus(BatchStatus.FAILED);
                }
                count++;
            }
        };
    }
}
