public class test237 {

    @Bean
    @ConditionalOnMissingBean
    public SchedulerFactoryBean quartzScheduler(QuartzProperties properties,
                                                ObjectProvider<SchedulerFactoryBeanCustomizer> customizers,
                                                ObjectProvider<JobDetail> jobDetails, Map<String, Calendar> calendars,
                                                ObjectProvider<Trigger> triggers, ApplicationContext applicationContext) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        SpringBeanJobFactory jobFactory = createJobFactory(applicationContext);
        schedulerFactoryBean.setJobFactory(jobFactory);
        setSchedulerNameIfNotNull(schedulerFactoryBean, properties);
        schedulerFactoryBean.setAutoStartup(properties.isAutoStartup());
        schedulerFactoryBean.setStartupDelay((int) properties.getStartupDelay().getSeconds());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setOverwriteExistingJobs(properties.isOverwriteExistingJobs());
        setQuartzPropertiesIfNotEmpty(schedulerFactoryBean, properties);
        schedulerFactoryBean.setJobDetails(jobDetails.orderedStream().toArray(JobDetail[]::new));
        schedulerFactoryBean.setCalendars(calendars);
        schedulerFactoryBean.setTriggers(triggers.orderedStream().toArray(Trigger[]::new));
        customizers.orderedStream().forEach((customizer) -> customizer.customize(schedulerFactoryBean));
        return schedulerFactoryBean;
    }

    private SpringBeanJobFactory createJobFactory(ApplicationContext applicationContext) {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    private void setSchedulerNameIfNotNull(SchedulerFactoryBean schedulerFactoryBean, QuartzProperties properties) {
        if (properties.getSchedulerName() != null) {
            schedulerFactoryBean.setSchedulerName(properties.getSchedulerName());
        }
    }

    private void setQuartzPropertiesIfNotEmpty(SchedulerFactoryBean schedulerFactoryBean, QuartzProperties properties) {
        if (!properties.getProperties().isEmpty()) {
            schedulerFactoryBean.setQuartzProperties(asProperties(properties.getProperties()));
        }
    }
}
