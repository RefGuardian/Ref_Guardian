public class test284 {

    private String createDescription() {
        StringBuilder description = new StringBuilder();
        description.append("an item named '").append(this.name).append("'");
        addOptionalInfo(description, " with dataType:", this.type);
        addOptionalInfo(description, " with sourceType:", this.sourceType);
        addOptionalInfo(description, " with sourceMethod:", this.sourceMethod);
        addOptionalInfo(description, " with defaultValue:", this.defaultValue);
        addOptionalInfo(description, " with description:", this.description);
        addOptionalInfo(description, " with deprecation:", this.deprecation);
        return description.toString();
    }

    private void addOptionalInfo(StringBuilder description, String label, String data) {
        if (data != null) {
            description.append(label).append(data);
        }
    }
}
