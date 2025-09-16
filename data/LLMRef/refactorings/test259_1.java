public class test259 {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        customizeSerializationInclusion(builder);
        customizeTimeZone(builder);
        configureFeatures(builder, FEATURE_DEFAULTS);
        configureVisibility(builder, this.jacksonProperties.getVisibility());
        configureFeatures(builder, this.jacksonProperties.getDeserialization());
        configureFeatures(builder, this.jacksonProperties.getSerialization());
        configureFeatures(builder, this.jacksonProperties.getMapper());
        configureFeatures(builder, this.jacksonProperties.getParser());
        configureFeatures(builder, this.jacksonProperties.getGenerator());
        configureFeatures(builder, this.jacksonProperties.getDatatype().getEnum());
        configureFeatures(builder, this.jacksonProperties.getDatatype().getJsonNode());
        configureDateFormat(builder);
        configurePropertyNamingStrategy(builder);
        configureModules(builder);
        configureLocale(builder);
        configureDefaultLeniency(builder);
        configureConstructorDetector(builder);
    }

    private void customizeSerializationInclusion(Jackson2ObjectMapperBuilder builder) {
        if (this.jacksonProperties.getDefaultPropertyInclusion() != null) {
            builder.serializationInclusion(this.jacksonProperties.getDefaultPropertyInclusion());
        }
    }

    private void customizeTimeZone(Jackson2ObjectMapperBuilder builder) {
        if (this.jacksonProperties.getTimeZone() != null) {
            builder.timeZone(this.jacksonProperties.getTimeZone());
        }
    }
}
