public class test225 {

    @Override
    public String toString() {
        boolean hasNames = hasNonEmptyNames();
        boolean hasTypes = hasNonEmptyTypes();
        boolean hasIgnoredTypes = hasNonEmptyIgnoredTypes();
        StringBuilder string = new StringBuilder();
        string.append("(");
        if (hasNames) {
            string.append("names: ");
            string.append(StringUtils.collectionToCommaDelimitedString(this.names));
            string.append(hasTypes ? " " : "; ");
        }
        if (hasTypes) {
            string.append("types: ");
            string.append(StringUtils.collectionToCommaDelimitedString(this.types));
            string.append(hasIgnoredTypes ? " " : "; ");
        }
        if (hasIgnoredTypes) {
            string.append("ignored: ");
            string.append(StringUtils.collectionToCommaDelimitedString(this.ignoredTypes));
            string.append("; ");
        }
        string.append("SearchStrategy: ");
        string.append(this.strategy.toString().toLowerCase(Locale.ENGLISH));
        string.append(")");
        return string.toString();
    }

    private boolean hasNonEmptyNames() {
        return !this.names.isEmpty();
    }

    private boolean hasNonEmptyTypes() {
        return !this.types.isEmpty();
    }

    private boolean hasNonEmptyIgnoredTypes() {
        return !this.ignoredTypes.isEmpty();
    }
}
