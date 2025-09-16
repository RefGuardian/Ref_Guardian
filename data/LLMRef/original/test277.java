public class test277 {

    @Test
    	void propertiesWithJavaBeanHierarchicalProperties() {
    		process(HierarchicalProperties.class,
    				Arrays.asList(HierarchicalPropertiesParent.class, HierarchicalPropertiesGrandparent.class),
    				(type, metadataEnv) -> {
    					PropertyDescriptorResolver resolver = new PropertyDescriptorResolver(metadataEnv);
    					assertThat(resolver.resolve(type, null).map(PropertyDescriptor::getName)).containsExactly("third",
    							"second", "first");
    					assertThat(resolver.resolve(type, null)
    						.map((descriptor) -> descriptor.getGetter().getEnclosingElement().getSimpleName().toString()))
    						.containsExactly("HierarchicalProperties", "HierarchicalPropertiesParent",
    								"HierarchicalPropertiesParent");
    					List<ItemMetadata> itemMetadataList = resolver.resolve(type, null)
    						.map((descriptor) -> descriptor.resolveItemMetadata("test", metadataEnv))
    						.toList();
    					assertThat(itemMetadataList).map(ItemMetadata::getDefaultValue)
    						.containsExactly("three", "two", "one");
    					assertThat(itemMetadataList).map(ItemMetadata::getDescription)
    						.containsExactly("Concrete property.", "Parent property.", "Grandparent property.");
    				});
    	}
}
