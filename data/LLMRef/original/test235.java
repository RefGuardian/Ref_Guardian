public class test235 {

    @Override
    	protected FailureAnalysis analyze(Throwable rootFailure, NoSuchBeanDefinitionException cause, String description) {
    		if (cause.getNumberOfBeansFound() != 0) {
    			return null;
    		}
    		List<AutoConfigurationResult> autoConfigurationResults = getAutoConfigurationResults(cause);
    		List<UserConfigurationResult> userConfigurationResults = getUserConfigurationResults(cause);
    		StringBuilder message = new StringBuilder();
    		message.append(String.format("%s required %s that could not be found.%n",
    				(description != null) ? description : "A component", getBeanDescription(cause)));
    		InjectionPoint injectionPoint = findInjectionPoint(rootFailure);
    		if (injectionPoint != null) {
    			Annotation[] injectionAnnotations = injectionPoint.getAnnotations();
    			if (injectionAnnotations.length > 0) {
    				message.append(String.format("%nThe injection point has the following annotations:%n"));
    				for (Annotation injectionAnnotation : injectionAnnotations) {
    					message.append(String.format("\t- %s%n", injectionAnnotation));
    				}
    			}
    		}
    		if (!autoConfigurationResults.isEmpty() || !userConfigurationResults.isEmpty()) {
    			message.append(String.format("%nThe following candidates were found but could not be injected:%n"));
    			for (AutoConfigurationResult result : autoConfigurationResults) {
    				message.append(String.format("\t- %s%n", result));
    			}
    			for (UserConfigurationResult result : userConfigurationResults) {
    				message.append(String.format("\t- %s%n", result));
    			}
    		}
    		String action = String.format("Consider %s %s in your configuration.",
    				(!autoConfigurationResults.isEmpty() || !userConfigurationResults.isEmpty())
    						? "revisiting the entries above or defining" : "defining",
    				getBeanDescription(cause));
    		return new FailureAnalysis(message.toString(), action, cause);
    	}
}
