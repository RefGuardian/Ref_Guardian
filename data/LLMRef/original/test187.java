public class test187 {

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
    			map.from(properties::getDisableHtmlEscaping).whenTrue().toCall(builder::disableHtmlEscaping);
    			map.from(properties::getDateFormat).to(builder::setDateFormat);
    		}
}
