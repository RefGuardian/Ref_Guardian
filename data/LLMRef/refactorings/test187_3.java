public class test187 {

    private static final String GENERATE_NON_EXECUTABLE_JSON = "generateNonExecutableJson";
    private static final String EXCLUDE_FIELDS_WITHOUT_EXPOSE_ANNOTATION = "excludeFieldsWithoutExposeAnnotation";
    private static final String SERIALIZE_NULLS = "serializeNulls";
    private static final String ENABLE_COMPLEX_MAP_KEY_SERIALIZATION = "enableComplexMapKeySerialization";
    private static final String DISABLE_INNER_CLASS_SERIALIZATION = "disableInnerClassSerialization";
    private static final String LONG_SERIALIZATION_POLICY = "longSerializationPolicy";
    private static final String FIELD_NAMING_POLICY = "fieldNamingPolicy";
    private static final String PRETTY_PRINTING = "prettyPrinting";
    private static final String STRICTNESS = "strictness";
    private static final String DISABLE_HTML_ESCAPING = "disableHtmlEscaping";
    private static final String DATE_FORMAT = "dateFormat";

    @Override
    public void customize(GsonBuilder builder) {
        GsonProperties properties = this.properties;
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties::getGenerateNonExecutableJson).whenTrue().toCall(builder::generateNonExecutableJson);
        map.from(properties::getExcludeFieldsWithoutExposeAnnotation)
            .whenTrue()
            .toCall(builder::excludeFieldsWithoutExposeAnnotation);
        map.from(properties::getSerializeNulls).whenTrue().toCall(builder::serializeNulls);
        map.from(properties::getEnableComplexMapKeySerialization)
            .whenTrue()
            .toCall(builder::enableComplexMapKeySerialization);
        map.from(properties::getDisableInnerClassSerialization)
            .whenTrue()
            .toCall(builder::disableInnerClassSerialization);
        map.from(properties::getLongSerializationPolicy).to(builder::setLongSerializationPolicy);
        map.from(properties::getFieldNamingPolicy).to(builder::setFieldNamingPolicy);
        map.from(properties::getPrettyPrinting).whenTrue().toCall(builder::setPrettyPrinting);
        map.from(properties::getStrictness).to(strictnessOrLeniency(builder));

        Function<GsonBuilder, ?> strictnessOrLeniency = properties::getStrictness;

        map.from(properties::getDisableHtmlEscaping).whenTrue().toCall(builder::disableHtmlEscaping);
        map.from(properties::getDateFormat).to(builder::setDateFormat);
    }
}
