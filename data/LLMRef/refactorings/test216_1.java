public class test216 {

    private ConditionOutcome getOutcome(String type) {
        if (type == null) {
            return null;
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnWebApplication.class);
        ClassNameFilter missingClassFilter = ClassNameFilter.MISSING;
        if (ConditionalOnWebApplication.Type.SERVLET.name().equals(type)) {
            if (missingClassFilter.matches(SERVLET_WEB_APPLICATION_CLASS, getBeanClassLoader())) {
                return ConditionOutcome.noMatch(message.didNotFind("servlet web application classes").atAll());
            }
        }
        if (ConditionalOnWebApplication.Type.REACTIVE.name().equals(type)) {
            if (missingClassFilter.matches(REACTIVE_WEB_APPLICATION_CLASS, getBeanClassLoader())) {
                return ConditionOutcome.noMatch(message.didNotFind("reactive web application classes").atAll());
            }
        }
        if (missingClassFilter.matches(SERVLET_WEB_APPLICATION_CLASS, getBeanClassLoader())
                && !ClassUtils.isPresent(REACTIVE_WEB_APPLICATION_CLASS, getBeanClassLoader())) {
            return ConditionOutcome.noMatch(message.didNotFind("reactive or servlet web application classes").atAll());
        }
        return null;
    }

    private ClassLoader getBeanClassLoader() {
        return null;
    }
}
