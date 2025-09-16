public class test285 {

    @Override
    public boolean matches(ConfigurationMetadata value) {
        return checkIfMetadataMatches(value);
    }

    private boolean checkIfMetadataMatches(ConfigurationMetadata value) {
        ItemMetadata itemMetadata = findItem(value, this.name);
        if (itemMetadata == null) {
            return false;
        }
        if (this.type != null && !this.type.equals(itemMetadata.getType())) {
            return false;
        }
        if (this.sourceType != null && !this.sourceType.getName().equals(itemMetadata.getSourceType())) {
            return false;
        }
        if (this.sourceMethod != null && !this.sourceMethod.equals(itemMetadata.getSourceMethod())) {
            return false;
        }
        if (this.defaultValue != null
                && !ObjectUtils.nullSafeEquals(this.defaultValue, itemMetadata.getDefaultValue())) {
            return false;
        }
        if (this.defaultValue == null && itemMetadata.getDefaultValue() != null) {
            return false;
        }
        if (this.description != null && !this.description.equals(itemMetadata.getDescription())) {
            return false;
        }
        if (this.deprecation == null && itemMetadata.getDeprecation() != null) {
            return false;
        }
        return this.deprecation == null || this.deprecation.equals(itemMetadata.getDeprecation());
    }
}
