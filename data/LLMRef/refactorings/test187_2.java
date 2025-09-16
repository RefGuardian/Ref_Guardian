public class test187 {

    private static final String GENERATE_NON_EXEC_JSON = "generateNonExecutableJson";
	private static final String EXCLUDE_FIELDS_WITHOUT_ANN = "excludeFieldsWithoutExposeAnnotation";
	private static final String SER_NULLS = "serializeNulls";
	private static final String COMPLEX_MAP_KEY_SER = "enableComplexMapKeySerialization";
	private static final String INNER_CLASS_SER = "disableInnerClassSerialization";
	private static final String LONG_SER_POL = "longSerializationPolicy";
	private static final String FIELD_NAMING_POL = "fieldNamingPolicy";
	private static final String PRETTY_PRINTING = "setPrettyPrinting";
	private static final String DISABLE_HTML_ESCAPING = "disableHtmlEscaping";
	private static final String DATE_FORMAT = "setDateFormat";

	@Override
	public void customize(GsonBuilder builder) {
		GsonProperties properties = this.properties;
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		map.from(properties::getGenerateNonExecutableJson).whenTrue().toCall(builder::generateNonExecutableJson);
		map.from(properties::getExcludeFieldsWithoutExposeAnnotation).whenTrue().toCall(builder::excludeFieldsWithoutExposeAnnotation);
		map.from(properties::getSerializeNulls).whenTrue().toCall(builder::serializeNulls);
		map.from(properties::getEnableComplexMapKeySerialization).whenTrue().toCall(builder::enableComplexMapKeySerialization);
		map.from(properties::getDisableInnerClassSerialization).whenTrue().toCall(builder::disableInnerClassSerialization);
		map.from(properties::getLongSerializationPolicy).to(builder::setLongSerializationPolicy);
		map.from(properties::getFieldNamingPolicy).to(builder::setFieldNamingPolicy);
		map.from(properties::getPrettyPrinting).whenTrue().toCall(builder::setPrettyPrinting);
		map.from(properties::getStrictness).to(strictnessOrLeniency(builder));
		map.from(properties::getDisableHtmlEscaping).whenTrue().toCall(builder::disableHtmlEscaping);
		map.from(properties::getDateFormat).to(builder::setDateFormat);
	}
}
