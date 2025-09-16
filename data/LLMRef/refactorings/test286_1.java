public class test286 {

    @Test
    void incrementalBuild() throws Exception {
        TestProject project = createProject();
        ConfigurationMetadata metadata = project.compile();
        verifyMetadata(metadata, "foo.counter", FooProperties.class, 0);
        verifyMetadata(metadata, "bar.counter", BarProperties.class, 0);
        metadata = project.compile();
        verifyMetadata(metadata, "foo.counter", FooProperties.class, 0);
        verifyMetadata(metadata, "bar.counter", BarProperties.class, 0);
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

    private TestProject createProject() {
        return new TestProject(FooProperties.class, BarProperties.class);
    }

    private void verifyMetadata(ConfigurationMetadata metadata, String property, Class<?> source, int defaultValue) {
        assertThat(metadata).has(Metadata.withProperty(property).fromSource(source).withDefaultValue(defaultValue));
    }
}
