public class test266 {

    @Test
    	void annotatedClass() {
    		compile(TestClassConfiguration.class, (properties) -> {
    			assertThat(properties).hasSize(7);
    			assertThat(properties).containsEntry(
    					"org.springframework.boot.autoconfigureprocessor.TestClassConfiguration.ConditionalOnClass",
    					"java.io.InputStream,org.springframework.boot.autoconfigureprocessor."
    							+ "TestClassConfiguration$Nested,org.springframework.foo");
    			assertThat(properties)
    				.containsKey(TestClassConfiguration.class.getName());
    			assertThat(properties)
    				.containsKey(TestClassConfiguration.Nested.class.getName());
    			assertThat(properties).containsEntry(
    					"org.springframework.boot.autoconfigureprocessor.TestClassConfiguration.ConditionalOnBean",
    					"java.io.OutputStream");
    			assertThat(properties).containsEntry(TestClassConfiguration.class.getName()
    					+ ".ConditionalOnSingleCandidate", "java.io.OutputStream");
    			assertThat(properties).containsEntry(TestClassConfiguration.class.getName()
    					+ ".ConditionalOnWebApplication", "SERVLET");
    		});
    	}
}
