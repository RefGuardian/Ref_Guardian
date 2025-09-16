public class test277 {

    @Test
    void propertiesWithJavaBeanHierarchicalProperties() {
        process(HierarchicalProperties.class,
                Arrays.asList(HierarchicalPropertiesParent.class, HierarchicalPropertiesGrandparent.class),
                (type, metadataEnv) -> {
                    PropertyDescriptorResolver resolver = new PropertyDescriptorResolver(metadataEnv);
                    assertPropertyDescriptorNames(resolver.resolveDescriptors(type, null));
                    assertEnclosingElementNames(resolver.resolveDescriptors(type, null));
                    List<ItemMetadata> itemMetadataList = assertDefaultValuesAndGetItemMetadata(resolver.resolveDescriptors(type, null), metadataEnv);
                    assertItemMetadataValues(itemMetadataList);
                });
    }

    private void assertPropertyDescriptorNames(Stream<PropertyDescriptor> descriptorStream) {
        assertThat(descriptorStream.map(PropertyDescriptor::getName)).containsExactly("third", "second", "first");
    }

    private void assertEnclosingElementNames(Stream<PropertyDescriptor> descriptorStream) {
        assertThat(descriptorStream.map((descriptor) -> descriptor.getGetter().getEnclosingElement().getSimpleName().toString()))
                .containsExactly("HierarchicalProperties", "HierarchicalPropertiesParent", "HierarchicalPropertiesParent");
    }

    private List<ItemMetadata> assertDefaultValuesAndGetItemMetadata(Stream<PropertyDescriptor> descriptorStream, MetadataEnv metadataEnv) {
        return descriptorStream
                .map((descriptor) -> descriptor.resolveItemMetadata("test", metadataEnv))
                .toList();
    }

    private void assertItemMetadataValues(List<ItemMetadata> itemMetadataList) {
        assertThat(itemMetadataList)
                .map(ItemMetadata::getDefaultValue)
                .containsExactly("three", "two", "one");

        assertThat(itemMetadataList)
                .map(ItemMetadata::getDescription)
                .containsExactly("Concrete property.", "Parent property.", "Grandparent property.");
    }
}
