public class test266 {

    @Test
    	void annotatedClass() {
    		compile(TestClassConfiguration.class, (properties) -> {
    			assertThat(properties).hasSize(7);
    			assertThat(properties).containsEntry(
    					"test266$ConditionalOnClass",
    					"java.io.InputStream,org.springframework.boot.autoconfigureprocessor."
    							+ "TestClassConfiguration$Nested,org.springframework.foo");
    			assertThat(properties)
    				.containsKey("test266");
    			assertThat(properties)
    				.containsKey("test266$Nested");
    			assertThat(properties).containsEntry(
    					"test266$ConditionalOnBean",
    					"java.io.OutputStream");
    			assertThat(properties).containsEntry("test266.ConditionalOnSingleCandidate", "java.io.OutputStream");
    			assertThat(properties).containsEntry("test266.ConditionalOnWebApplication", "SERVLET");
    		});
    	}
}
