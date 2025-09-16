public class test277 {

    @Test
	void propertiesWithJavaBeanHierarchicalProperties() {
		process(HierarchicalProperties.class,
				Arrays.asList(HierarchicalPropertiesParent.class, HierarchicalPropertiesGrandparent.class),
				(type, metadataEnv) -> {
					PropertyDescriptorResolver resolver = new PropertyDescriptorResolver(metadataEnv);
					assertResolve(resolver.resolve(type, null).map(PropertyDescriptor::getName)).containsExactly("third",
							"second", "first");
					assertResolve(resolver.resolve(type, null)
						.map((descriptor) -> descriptor.getGetter().getEnclosingElement().getSimpleName().toString()))
						.containsExactly("HierarchicalProperties", "HierarchicalPropertiesParent",
								"HierarchicalPropertiesParent");
					List<ItemMetadata> itemMetadataList = assertResolve(resolver.resolve(type, null)
						.map((descriptor) -> descriptor.resolveItemMetadata("test", metadataEnv))
						.toList();
					assertResolve(itemMetadataList).map(ItemMetadata::getDefaultValue)
						.containsExactly("three", "two", "one");
					assertResolve(itemMetadataList).map(ItemMetadata::getDescription)
						.containsExactly("Concrete property.", "Parent property.", "Grandparent property.");
				});
	}

}
