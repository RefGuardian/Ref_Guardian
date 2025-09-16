public class test18 {

    @SuppressWarnings("unchecked")
    	<T> T createSpy(String name, Object instance) {
    		Assert.notNull(instance, "'instance' must not be null");
    		Assert.isInstanceOf(this.typeToSpy.resolve(), instance);
    		if (Mockito.mockingDetails(instance).isSpy()) {
    			return (T) instance;
    		}
    		MockSettings settings = MockReset.withSettings(getReset());
    		if (StringUtils.hasLength(name)) {
    			settings.name(name);
    		}
    		if (isProxyTargetAware()) {
    			settings.verificationStartedListeners(new SpringAopBypassingVerificationStartedListener());
    		}
    		settings.defaultAnswer(AdditionalAnswers.delegatesTo(instance));
    		return (T) createSpyWithSettings(instance, settings);
    	}

    	private <T> T createSpyWithSettings(Object instance, MockSettings settings) {
    		Class<?> toSpy;
    		if (Proxy.isProxyClass(instance.getClass())) {
    			toSpy = this.typeToSpy.toClass();
    		}
    		else {
    			settings.defaultAnswer(Mockito.CALLS_REAL_METHODS);
    			settings.spiedInstance(instance);
    			toSpy = instance.getClass();
    		}
    		return (T) mock(toSpy, settings);
    	}
}
