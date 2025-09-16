public class test132 {

    @Override
    				public Job createJob() {
    					AbstractJob job = createAbstractJob();
    					job.setJobRepository(jobRepository);
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
                execution.setStatus(BatchStatus.COMPLETED);
            }
        };
    }
}
