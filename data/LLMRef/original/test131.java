public class test131 {

    private JobFactory getJobFactory() {
    			JobRepository jobRepository = this.applicationContext.getBean(JobRepository.class);
    			return new JobFactory() {
    
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
    
    				@Override
    				public String getJobName() {
    					return "discreteRegisteredJob";
    				}
    
    			};
    		}
}
