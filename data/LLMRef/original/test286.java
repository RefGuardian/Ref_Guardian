public class test286 {

    @Test
    	void incrementalBuild() throws Exception {
    		TestProject project = new TestProject(FooProperties.class, BarProperties.class);
    		ConfigurationMetadata metadata = project.compile();
    		assertThat(metadata)
    			.has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(0));
    		assertThat(metadata)
    			.has(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(0));
    		metadata = project.compile();
    		assertThat(metadata)
    			.has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(0));
    		assertThat(metadata)
    			.has(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(0));
    		project.addSourceCode(BarProperties.class, BarProperties.class.getResourceAsStream("BarProperties.snippet"));
    		metadata = project.compile();
    		assertThat(metadata).has(Metadata.withProperty("bar.extra"));
    		assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(0));
    		assertThat(metadata).has(Metadata.withProperty("bar.counter").withDefaultValue(0));
    		project.revert(BarProperties.class);
    		metadata = project.compile();
    		assertThat(metadata).isNotEqualTo(Metadata.withProperty("bar.extra"));
    		assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(0));
    		assertThat(metadata).has(Metadata.withProperty("bar.counter").withDefaultValue(0));
    	}
}
