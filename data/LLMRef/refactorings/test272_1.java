public class test272 {

    private void interceptMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
    			ExtensionContext extensionContext) throws Throwable {
    		if (isModifiedClassPathClassLoader(extensionContext)) {
    			invocation.proceed();
    			return;
    		}
    		Class<?> testClass = extensionContext.getRequiredTestClass();
    		Method testMethod = invocationContext.getExecutable();
    		URLClassLoader modifiedClassLoader = ModifiedClassPathClassLoader.get(testClass, testMethod,
    				invocationContext.getArguments());
    		if (modifiedClassLoader == null) {
    			invocation.proceed();
    			return;
    		}
    		invocation.skip();
    		try {
    			ModifiedClassPathClassLoader.runTestWithModifiedClassLoader(extensionContext.getUniqueId(), modifiedClassLoader);
    		}
    		finally {
    		}
    	}
}
