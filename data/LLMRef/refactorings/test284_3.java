public class test284 {

    private String createDescription() {
        return generateDescription();
    }

    private String generateDescription() {
        StringBuilder description = new StringBuilder();
        description.append("an item named '").append(this.name).append("'");
        if (this.type != null) {
            description.append(" with dataType:").append(this.type);
        }
        if (this.sourceType != null) {
            description.append(" with sourceType:").append(this.sourceType);
        }
        if (this.sourceMethod != null) {
            description.append(" with sourceMethod:").append(this.sourceMethod);
        }
        if (this.defaultValue != null) {
            description.append(" with defaultValue:").append(this.defaultValue);
        }
        if (this.description != null) {
            description.append(" with description:").append(this.description);
        }
        if (this.deprecation != null) {
            description.append(" with deprecation:").append(this.deprecation);
        }
        return description.toString();
    }
}
