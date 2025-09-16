public class test196 {

    private static final String DEFAULT_DISPATCHER_SERVLET_BEAN_NAME = "defaultDispatcherServlet";

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder message = ConditionMessage.forCondition("Default DispatcherServlet");
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        List<String> dispatchServletBeans = Arrays
            .asList(beanFactory.getBeanNamesForType(DispatcherServlet.class, false, false));
        if (dispatchServletBeans.contains(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)) {
            return ConditionOutcome
                .noMatch(message.found("dispatcher servlet bean").items(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME));
        }
        if (beanFactory.containsBean(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)) {
            return ConditionOutcome
                .noMatch(message.found("non dispatcher servlet bean").items(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME));
        }
        if (dispatchServletBeans.isEmpty()) {
            return ConditionOutcome.match(message.didNotFind("dispatcher servlet beans").atAll());
        }
        return ConditionOutcome.match(message.found("dispatcher servlet bean", "dispatcher servlet beans")
            .items(Style.QUOTE, dispatchServletBeans)
            .append("and none is named " + DEFAULT_DISPATCHER_SERVLET_BEAN_NAME));
    }
}
