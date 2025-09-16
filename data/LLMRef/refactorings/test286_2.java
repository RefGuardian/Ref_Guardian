public class test286 {

    private static final int DEFAULT_VALUE = 0;

    @Test
    void incrementalBuild() throws Exception {
        TestProject project = new TestProject(FooProperties.class, BarProperties.class);
        ConfigurationMetadata metadata = project.compile();
        assertThat(metadata)
                .has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(DEFAULT_VALUE));
        assertThat(metadata)
                .has(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(DEFAULT_VALUE));
        metadata = project.compile();
        assertThat(metadata)
                .has(Metadata.withProperty("foo.counter").fromSource(FooProperties.class).withDefaultValue(DEFAULT_VALUE));
        assertThat(metadata)
                .has(Metadata.withProperty("bar.counter").fromSource(BarProperties.class).withDefaultValue(DEFAULT_VALUE));
        project.addSourceCode(BarProperties.class, BarProperties.class.getResourceAsStream("BarProperties.snippet"));
        metadata = project.compile();
        assertThat(metadata).has(Metadata.withProperty("bar.extra"));
        assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(DEFAULT_VALUE));
        assertThat(metadata).has(Metadata.withProperty("bar.counter").withDefaultValue(DEFAULT_VALUE));
        project.revert(BarProperties.class);
        metadata = project.compile();
        assertThat(metadata).isNotEqualTo(Metadata.withProperty("bar.extra"));
        assertThat(metadata).has(Metadata.withProperty("foo.counter").withDefaultValue(DEFAULT_VALUE));
        assertThat(metadata).has(Metadata.withProperty("bar.counter").withDefaultValue(DEFAULT_VALUE));
    }
}
