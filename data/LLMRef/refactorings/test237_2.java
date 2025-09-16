public class test237 {

    @Bean
    	@ConditionalOnMissingBean
    	public SchedulerFactoryBean quartzScheduler(QuartzProperties properties,
    			ObjectProvider<SchedulerFactoryBeanCustomizer> customizers, ObjectProvider<JobDetail> jobDetails,
    			Map<String, Calendar> calendars, ObjectProvider<Trigger> triggers, ApplicationContext applicationContext) {
    		SchedulerFactoryBean schedulerFactoryBean = createSchedulerFactoryBean(properties, customizers, jobDetails, calendars, triggers, applicationContext);
    		return schedulerFactoryBean;
    	}

    private SchedulerFactoryBean createSchedulerFactoryBean(QuartzProperties properties,
                                                           ObjectProvider<SchedulerFactoryBeanCustomizer> customizers,
                                                           ObjectProvider<JobDetail> jobDetails, Map<String, Calendar> calendars,
                                                           ObjectProvider<Trigger> triggers, ApplicationContext applicationContext) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(jobFactory);
        if (properties.getSchedulerName() != null) {
            schedulerFactoryBean.setSchedulerName(properties.getSchedulerName());
        }
        schedulerFactoryBean.setAutoStartup(properties.isAutoStartup());
        schedulerFactoryBean.setStartupDelay((int) properties.getStartupDelay().getSeconds());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setOverwriteExistingJobs(properties.isOverwriteExistingJobs());
        if (!properties.getProperties().isEmpty()) {
            schedulerFactoryBean.setQuartzProperties(asProperties(properties.getProperties()));
        }
        schedulerFactoryBean.setJobDetails(jobDetails.orderedStream().toArray(JobDetail[]::new));
        schedulerFactoryBean.setCalendars(calendars);
        schedulerFactoryBean.setTriggers(triggers.orderedStream().toArray(Trigger[]::new));
        customizers.orderedStream().forEach((customizer) -> customizer.customize(schedulerFactoryBean));
        return schedulerFactoryBean;
    }
}
