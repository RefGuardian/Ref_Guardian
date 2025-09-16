public class test197 {

    private ConditionOutcome checkServletRegistration(ConfigurableListableBeanFactory beanFactory) {
        ConditionMessage.Builder message = startMessage();
        List<String> registrations = extractRegistrations(beanFactory);
        boolean containsDispatcherRegistrationBean = beanFactory.containsBean(DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        if (registrations.isEmpty()) {
            if (containsDispatcherRegistrationBean) {
                return ConditionOutcome.noMatch(message.found("non servlet registration bean")
                    .items(DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME));
            }
            return ConditionOutcome.match(message.didNotFind("servlet registration bean").atAll());
        }
        if (registrations.contains(DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)) {
            return ConditionOutcome.noMatch(message.found("servlet registration bean")
                .items(DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME));
        }
        if (containsDispatcherRegistrationBean) {
            return ConditionOutcome.noMatch(message.found("non servlet registration bean")
                .items(DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME));
        }
        return ConditionOutcome.match(message.found("servlet registration beans")
            .items(Style.QUOTE, registrations)
            .append("and none is named " + DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME));
    }

    private List<String> extractRegistrations(ConfigurableListableBeanFactory beanFactory) {
        return Arrays.asList(beanFactory.getBeanNamesForType(ServletRegistrationBean.class, false, false));
    }
}
