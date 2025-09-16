public class test187 {

    private static final String GENERATE_NON_EXECUTABLE_JSON = properties::getGenerateNonExecutableJson;
    private static final String EXCLUDE_FIELDS_WITHOUT_EXPOSE_ANNOTATION = properties::getExcludeFieldsWithoutExposeAnnotation;
    private static final String SERIALIZE_NULLS = properties::getSerializeNulls;
    private static final String ENABLE_COMPLEX_MAP_KEY_SERIALIZATION = properties::getEnableComplexMapKeySerialization;
    private static final String DISABLE_INNER_CLASS_SERIALIZATION = properties::getDisableInnerClassSerialization;
    private static final String DATE_FORMAT = properties::getDateFormat;

    @Override
    public void customize(GsonBuilder builder) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(GENERATE_NON_EXECUTABLE_JSON).whenTrue().toCall(builder::generateNonExecutableJson);
        map.from(EXCLUDE_FIELDS_WITHOUT_EXPOSE_ANNOTATION).whenTrue().toCall(builder::excludeFieldsWithoutExposeAnnotation);
        map.from(SERIALIZE_NULLS).whenTrue().toCall(builder::serializeNulls);
        map.from(ENABLE_COMPLEX_MAP_KEY_SERIALIZATION).whenTrue().toCall(builder::enableComplexMapKeySerialization);
        map.from(DISABLE_INNER_CLASS_SERIALIZATION).whenTrue().toCall(builder::disableInnerClassSerialization);
        map.from(properties::getLongSerializationPolicy).to(builder::setLongSerializationPolicy);
        map.from(properties::getFieldNamingPolicy).to(builder::setFieldNamingPolicy);
        map.from(properties::getPrettyPrinting).whenTrue().toCall(builder::setPrettyPrinting);
        map.from(properties::getStrictness).to(strictnessOrLeniency(builder));
        map.from(properties::getDisableHtmlEscaping).whenTrue().toCall(builder::disableHtmlEscaping);
        map.from(DATE_FORMAT).to(builder::setDateFormat);
    }
}
