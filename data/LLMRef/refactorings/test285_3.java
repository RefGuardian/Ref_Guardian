public class test285 {

    @Override
    public boolean matches(ConfigurationMetadata value) {
        ItemMetadata itemMetadata = findItem(value, this.name);
        if (itemMetadata == null) {
            return false;
        }
        if (!areTypesMatching(itemMetadata)) {
            return false;
        }
        if (!areSourceTypesMatching(itemMetadata)) {
            return false;
        }
        if (!areSourceMethodsMatching(itemMetadata)) {
            return false;
        }
        if (!areDefaultValuesMatching(itemMetadata)) {
            return false;
        }
        if (!areDescriptionsMatching(itemMetadata)) {
            return false;
        }
        if (!areDeprecationsMatching(itemMetadata)) {
            return false;
        }
        return true;
    }

    private boolean areTypesMatching(ItemMetadata itemMetadata) {
        return (this.type == null || this.type.equals(itemMetadata.getType()));
    }

    private boolean areSourceTypesMatching(ItemMetadata itemMetadata) {
        return (this.sourceType == null || this.sourceType.getName().equals(itemMetadata.getSourceType()));
    }

    private boolean areSourceMethodsMatching(ItemMetadata itemMetadata) {
        return (this.sourceMethod == null || this.sourceMethod.equals(itemMetadata.getSourceMethod()));
    }

    private boolean areDefaultValuesMatching(ItemMetadata itemMetadata) {
        return (this.defaultValue == null && itemMetadata.getDefaultValue() == null) ||
                ObjectUtils.nullSafeEquals(this.defaultValue, itemMetadata.getDefaultValue());
    }

    private boolean areDescriptionsMatching(ItemMetadata itemMetadata) {
        return (this.description == null || this.description.equals(itemMetadata.getDescription()));
    }

    private boolean areDeprecationsMatching(ItemMetadata itemMetadata) {
        return (this.deprecation == null || this.deprecation.equals(itemMetadata.getDeprecation()));
    }
}
