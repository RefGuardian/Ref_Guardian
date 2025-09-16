public class test285 {

    @Override
    public boolean matches(ConfigurationMetadata value) {
        ItemMetadata itemMetadata = findItem(value, this.name);
        if (itemMetadata == null) {
            return false;
        }
        if (!checkType(itemMetadata)) {
            return false;
        }
        if (!checkSourceType(itemMetadata)) {
            return false;
        }
        if (!checkSourceMethod(itemMetadata)) {
            return false;
        }
        if (!checkDefaultValue(itemMetadata)) {
            return false;
        }
        if (checkNullDefault(itemMetadata)) {
            return false;
        }
        if (!checkDescription(itemMetadata)) {
            return false;
        }
        if (!checkDeprecation(itemMetadata)) {
            return false;
        }
        return checkDeprecationEquals(itemMetadata);
    }

    private boolean checkType(ItemMetadata itemMetadata) {
        return this.type == null || this.type.equals(itemMetadata.getType());
    }

    private boolean checkSourceType(ItemMetadata itemMetadata) {
        return this.sourceType == null || this.sourceType.getName().equals(itemMetadata.getSourceType());
    }

    private boolean checkSourceMethod(ItemMetadata itemMetadata) {
        return this.sourceMethod == null || this.sourceMethod.equals(itemMetadata.getSourceMethod());
    }

    private boolean checkDefaultValue(ItemMetadata itemMetadata) {
        return this.defaultValue == null || ObjectUtils.nullSafeEquals(this.defaultValue, itemMetadata.getDefaultValue());
    }

    private boolean checkNullDefault(ItemMetadata itemMetadata) {
        return this.defaultValue == null && itemMetadata.getDefaultValue() != null;
    }

    private boolean checkDescription(ItemMetadata itemMetadata) {
        return this.description == null || this.description.equals(itemMetadata.getDescription());
    }

    private boolean checkDeprecation(ItemMetadata itemMetadata) {
        return this.deprecation == null && itemMetadata.getDeprecation() != null;
    }

    private boolean checkDeprecationEquals(ItemMetadata itemMetadata) {
        return this.deprecation == null || this.deprecation.equals(itemMetadata.getDeprecation());
    }
}
