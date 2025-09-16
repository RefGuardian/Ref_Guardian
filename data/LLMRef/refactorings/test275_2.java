public class test275 {

    @Test
	void mergeExistingPropertyWithSeveralCandidates() throws Exception {
		ItemMetadata property = createProperty();
		String additionalMetadata = buildAdditionalMetadata(property);
		ConfigurationMetadata metadata = compile(additionalMetadata, SimpleProperties.class, SimpleConflictingProperties.class);
		assertThat(metadata.getItems()).hasSize(6);
		List<ItemMetadata> items = getMatchingItems(metadata);
		assertThat(items).hasSize(2);
		ItemMetadata matchingProperty = findMatchingProperty(items);
		assertThat(matchingProperty).isNotNull();
		assertThat(matchingProperty.getDefaultValue()).isEqualTo(true);
		assertThat(matchingProperty.getSourceType()).isEqualTo(SimpleProperties.class.getName());
		assertThat(matchingProperty.getDescription()).isEqualTo("A simple flag.");
		ItemMetadata nonMatchingProperty = findNonMatchingProperty(items);
		assertThat(nonMatchingProperty).isNotNull();
		assertThat(nonMatchingProperty.getDefaultValue()).isEqualTo("hello");
		assertThat(nonMatchingProperty.getSourceType()).isEqualTo(SimpleConflictingProperties.class.getName());
		assertThat(nonMatchingProperty.getDescription()).isNull();
	}

	private ItemMetadata createProperty() {
		return ItemMetadata.newProperty("simple", "flag", Boolean.class.getName(), null, null, null, true, null);
	}

	private List<ItemMetadata> getMatchingItems(ConfigurationMetadata metadata) {
		return metadata.getItems().stream().filter((item) -> item.getName().equals("simple.flag")).collect(Collectors.toList());
	}

	private ItemMetadata findMatchingProperty(List<ItemMetadata> items) {
		return items.stream().filter((item) -> item.getType().equals(Boolean.class.getName())).findFirst().orElse(null);
	}

	private ItemMetadata findNonMatchingProperty(List<ItemMetadata> items) {
		return items.stream().filter((item) -> item.getType().equals(String.class.getName())).findFirst().orElse(null);
	}
}
