public class test288 {

    @Test
    void innerClassProperties() {
        ConfigurationMetadata metadata = compile(InnerClassProperties.class);
        assertMetadata(metadata, "config", InnerClassProperties.class);
        assertMetadata(metadata, "config.first", InnerClassProperties.Foo.class, InnerClassProperties.class);
        assertProperty(metadata, "config.first.name");
        assertProperty(metadata, "config.first.bar.name");
        assertMetadata(metadata, "config.the-second", InnerClassProperties.Foo.class, InnerClassProperties.class);
        assertProperty(metadata, "config.the-second.name");
        assertProperty(metadata, "config.the-second.bar.name");
        assertMetadata(metadata, "config.third", SimplePojo.class, InnerClassProperties.class);
        assertProperty(metadata, "config.third.value");
        assertProperty(metadata, "config.fourth");
        assertNotEqualTo(metadata, "config.fourth");
        assertMetadata(metadata, "config.fifth", DeprecatedSimplePojo.class, InnerClassProperties.class);
        assertProperty(metadata, "config.fifth.value").withDeprecation();
    }

    private void assertMetadata(ConfigurationMetadata metadata, String group, Class<?> type, Class<?> source) {
        assertThat(metadata).has(Metadata.withGroup(group).ofType(type).fromSource(source));
    }

    private void assertProperty(ConfigurationMetadata metadata, String property) {
        assertThat(metadata).has(Metadata.withProperty(property));
    }

    private void assertNotEqualTo(ConfigurationMetadata metadata, String group) {
        assertThat(metadata).isNotEqualTo(Metadata.withGroup(group));
    }
}
