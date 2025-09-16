public class test130 {

    @Bean
    		Job discreteJob() {
    			AbstractJob job = new AbstractJob("discreteRegisteredJob") {
    
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
    					if (count == 0) {
    						execution.setStatus(BatchStatus.COMPLETED);
    					}
    					else {
    						execution.setStatus(BatchStatus.FAILED);
    					}
    					count++;
    				}
    			};
    			job.setJobRepository(this.jobRepository);
    			return job;
    		}
}
