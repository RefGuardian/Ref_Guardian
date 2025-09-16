public class test134 {

    @Bean
    		Job job() {
    			AbstractJob job = createAbstractJob();
    			job.setJobRepository(this.jobRepository);
    			return job;
    		}
    
    private AbstractJob createAbstractJob() {
        return new AbstractJob() {
    
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
