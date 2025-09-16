public class test216 {

    private ConditionOutcome getOutcome(String type) {
        if (type == null) {
            return null;
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnWebApplication.class);
        ClassNameFilter missingClassFilter = ClassNameFilter.MISSING;
        
        if (ConditionalOnWebApplication.Type.SERVLET.name().equals(type)) {
            if (isServletClassMissing(missingClassFilter)) {
                return ConditionOutcome.noMatch(message.didNotFind("servlet web application classes").atAll());
            }
        }
        
        if (ConditionalOnWebApplication.Type.REACTIVE.name().equals(type)) {
            if (isReactiveClassMissing(missingClassFilter)) {
                return ConditionOutcome.noMatch(message.didNotFind("reactive web application classes").atAll());
            }
        }
        
        if (isServletClassMissing(missingClassFilter) && !isReactiveClassPresent()) {
            return ConditionOutcome.noMatch(message.didNotFind("reactive or servlet web application classes").atAll());
        }
        
        return null;
    }

    private boolean isServletClassMissing(ClassNameFilter missingClassFilter) {
        return missingClassFilter.matches(SERVLET_WEB_APPLICATION_CLASS, getBeanClassLoader());
    }

    private boolean isReactiveClassMissing(ClassNameFilter missingClassFilter) {
        return missingClassFilter.matches(REACTIVE_WEB_APPLICATION_CLASS, getBeanClassLoader());
    }

    private boolean isReactiveClassPresent() {
        return ClassUtils.isPresent(REACTIVE_WEB_APPLICATION_CLASS, getBeanClassLoader());
    }
}
