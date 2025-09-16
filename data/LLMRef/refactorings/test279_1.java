public class test279 {

    @Test
	void marshallAndUnmarshal() throws Exception {
		ConfigurationMetadata metadata = createConfigurationMetadata();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JsonMarshaller marshaller = new JsonMarshaller();
		marshaller.write(metadata, outputStream);
		ConfigurationMetadata read = marshaller.read(new ByteArrayInputStream(outputStream.toByteArray()));
		assertMetadata(read, metadata);
	}

	private static ConfigurationMetadata createConfigurationMetadata() {
		ConfigurationMetadata metadata = new ConfigurationMetadata();
		metadata.add(createPropertyItem("a", "b", StringBuffer.class.getName(), InputStream.class.getName(), null,
				"desc", "x", new ItemDeprecation("Deprecation comment", "b.c.d", "1.2.3")));
		metadata.add(createPropertyItem("b.c.d", null, null, null, null, null, null, null));
		metadata.add(createPropertyItem("c", null, null, null, null, null, 123, null));
		metadata.add(createPropertyItem("d", null, null, null, null, null, true, null));
		metadata.add(createPropertyItem("e", null, null, null, null, null, new String[] { "y", "n" }, null));
		metadata.add(createPropertyItem("f", null, null, null, null, null, new Boolean[] { true, false }, null));
		metadata.add(ItemMetadata.newGroup("d", null, null, null));
		metadata.add(ItemMetadata.newGroup("e", null, null, "sourceMethod"));
		metadata.add(ItemHint.newHint("a.b"));
		metadata.add(ItemHint.newHint("c", new ItemHint.ValueHint(123, "hey"), new ItemHint.ValueHint(456, null));
		metadata.add(new ItemHint("d", null,
				Arrays.asList(new ItemHint.ValueProvider("first", Collections.singletonMap("target", "foo")),
						new ItemHint.ValueProvider("second", null)));
		return metadata;
	}

	private static ItemMetadata createPropertyItem(String name, String type, String valueType, String sourceType,
			String sourceMethod, String description, Object defaultValue,
			ItemDeprecation deprecation) {
		return ItemMetadata.newProperty(name, type, valueType, sourceType, sourceMethod, description, defaultValue,
				deprecation);
	}

	private static void assertMetadata(ConfigurationMetadata read, ConfigurationMetadata metadata) {
		assertThat(read).has(Metadata.withProperty("a.b", StringBuffer.class)
			.fromSource(InputStream.class)
			.withDescription("desc")
			.withDefaultValue("x")
			.withDeprecation("Deprecation comment", "b.c.d", "1.2.3"));
		assertThat(read).has(Metadata.withProperty("b.c.d"));
		assertThat(read).has(Metadata.withProperty("c").withDefaultValue(123));
		assertThat(read).has(Metadata.withProperty("d").withDefaultValue(true));
		assertThat(read).has(Metadata.withProperty("e").withDefaultValue(new String[] { "y", "n" }));
		assertThat(read).has(Metadata.withProperty("f").withDefaultValue(new Object[] { true, false }));
		assertThat(read).has(Metadata.withGroup("d"));
		assertThat(read).has(Metadata.withGroup("e").fromSourceMethod("sourceMethod"));
		assertThat(read).has(Metadata.withHint("a.b"));
		assertThat(read).has(Metadata.withHint("c").withValue(0, 123, "hey").withValue(1, 456, null));
		assertThat(read).has(Metadata.withHint("d").withProvider("first", "target", "foo").withProvider("second"));
	}
}
