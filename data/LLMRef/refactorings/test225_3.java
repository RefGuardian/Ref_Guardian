public class test225 {

    @Override
    public String toString() {
        boolean hasNames = !this.names.isEmpty();
        boolean hasTypes = !this.types.isEmpty();
        boolean hasIgnoredTypes = !this.ignoredTypes.isEmpty();
        StringBuilder string = new StringBuilder();
        string.append("(");
        string = extractMethodAppendString(hasNames, string, "names", this.names, hasTypes, hasIgnoredTypes);
        string = extractMethodAppendString(hasTypes, string, "types", this.types, hasIgnoredTypes, false);
        string = extractMethodAppendString(hasIgnoredTypes, string, "ignored", this.ignoredTypes, false, true);
        string.append("SearchStrategy: ");
        string.append(this.strategy.toString().toLowerCase(Locale.ENGLISH));
        string.append(")");
        return string.toString();
    }

    private StringBuilder extractMethodAppendString(boolean condition, StringBuilder string, String label, List<?> collection, boolean nextCondition, boolean isLast) {
        if (condition) {
            string.append(label).append(": ");
            string.append(StringUtils.collectionToCommaDelimitedString(collection));
            string.append(nextCondition ? " " : "; ");
        }
        return string;
    }
}
