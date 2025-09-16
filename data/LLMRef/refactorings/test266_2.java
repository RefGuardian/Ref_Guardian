public class test266 {

    @Test
    	void annotatedClass() {
    		compile(TestClassConfiguration.class, (properties) -> {
    			assertThat(properties.size()).isEqualTo(7);
    			assertThat(properties).containsEntry(
    					"org.springframework.boot.autoconfigureprocessor.TestClassConfiguration.ConditionalOnClass",
    					"java.io.InputStream,org.springframework.boot.autoconfigureprocessor."
    							+ "TestClassConfiguration$Nested,org.springframework.foo");
    			assertThat(properties)
    				.containsKey("org.springframework.boot.autoconfigureprocessor.TestClassConfiguration");
    			assertThat(properties)
    				.containsKey("org.springframework.boot.autoconfigureprocessor.TestClassConfiguration$Nested");
    			assertThat(properties).containsEntry(
    					"org.springframework.boot.autoconfigureprocessor.TestClassConfiguration.ConditionalOnBean",
    					"java.io.OutputStream");
    			assertThat(properties).containsEntry("org.springframework.boot.autoconfigureprocessor."
    					+ "TestClassConfiguration.ConditionalOnSingleCandidate", "java.io.OutputStream");
    			assertThat(properties).containsEntry("org.springframework.boot.autoconfigureprocessor."
    					+ "TestClassConfiguration.ConditionalOnWebApplication", "SERVLET");
    		});
    	}
}
