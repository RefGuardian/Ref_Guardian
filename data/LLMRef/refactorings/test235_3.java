public class test235 {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, NoSuchBeanDefinitionException cause, String description) {
        if (cause.getNumberOfBeansFound() != 0) {
            return null;
        }

        List<AutoConfigurationResult> autoConfigurationResults = getAutoConfigurationResults(cause);
        List<UserConfigurationResult> userConfigurationResults = getUserConfigurationResults(cause);
        StringBuilder message = createFailureMessage(description, cause);

        InjectionPoint injectionPoint = findInjectionPoint(rootFailure);
        message = updateMessageWithInjectionAnnotations(message, injectionPoint);

        message = updateMessageWithCandidates(autoConfigurationResults, userConfigurationResults, message);

        String action = createFailureAction(autoConfigurationResults, userConfigurationResults, cause);

        return new FailureAnalysis(message.toString(), action, cause);
    }

    private StringBuilder createFailureMessage(String description, NoSuchBeanDefinitionException cause) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("%s required %s that could not be found.%n",
                (description != null) ? description : "A component", getBeanDescription(cause)));
        return message;
    }

    private StringBuilder updateMessageWithInjectionAnnotations(StringBuilder message, InjectionPoint injectionPoint) {
        if (injectionPoint != null) {
            Annotation[] injectionAnnotations = injectionPoint.getAnnotations();
            if (injectionAnnotations.length > 0) {
                message.append(String.format("%nThe injection point has the following annotations:%n"));
                for (Annotation injectionAnnotation : injectionAnnotations) {
                    message.append(String.format("\t- %s%n", injectionAnnotation));
                }
            }
        }
        return message;
    }

    private StringBuilder updateMessageWithCandidates(List<AutoConfigurationResult> autoConfigurationResults,
                                                      List<UserConfigurationResult> userConfigurationResults, StringBuilder message) {
        if (!autoConfigurationResults.isEmpty() || !userConfigurationResults.isEmpty()) {
            message.append(String.format("%nThe following candidates were found but could not be injected:%n"));
            for (AutoConfigurationResult result : autoConfigurationResults) {
                message.append(String.format("\t- %s%n", result));
            }
            for (UserConfigurationResult result : userConfigurationResults) {
                message.append(String.format("\t- %s%n", result));
            }
        }
        return message;
    }

    private String createFailureAction(List<AutoConfigurationResult> autoConfigurationResults,
                                       List<UserConfigurationResult> userConfigurationResults, NoSuchBeanDefinitionException cause) {
        return String.format("Consider %s %s in your configuration.",
                (!autoConfigurationResults.isEmpty() || !userConfigurationResults.isEmpty())
                        ? "revisiting the entries above or defining" : "defining",
                getBeanDescription(cause));
    }
}
