public class test132 {

    @Override
    				public Job createJob() {
    					AbstractJob job = new AbstractJob("discreteRegisteredJob") {
    
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
    					job.setJobRepository(jobRepository);
    					return job;
    				}
}
