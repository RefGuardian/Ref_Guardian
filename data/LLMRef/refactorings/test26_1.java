public class test26 {

    /**
     * Create a new {@link ImageReference} from the given value. The following value forms
     * can be used:
     * <ul>
     * <li>{@code name} (maps to {@code docker.io/library/name})</li>
     * <li>{@code domain/name}</li>
     * <li>{@code domain:port/name}</li>
     * <li>{@code domain:port/name:tag}</li>
     * <li>{@code domain:port/name@digest}</li>
     * </ul>
     * @param value the value to parse
     * @return an {@link ImageReference} instance
     */
    public static ImageReference of(String value) {
        Assert.hasText(value, "'value' must not be null");
        String domain = ImageName.parseDomain(value);
        String path = extractPath(value, domain);
        String digest = extractDigest(path);
        String tag = extractTag(path);
        Assert.isTrue(Regex.PATH.matcher(path).matches(),
                () -> "'value' path must contain an image reference in the form "
                        + "'[domainHost:port/][path/]name[:tag][@digest] "
                        + "(with 'path' and 'name' containing only [a-z0-9][.][_][-]) [" + value + "]");
        ImageName name = new ImageName(domain, path);
        return new ImageReference(name, tag, digest);
    }

    private static String extractPath(String value, String domain) {
        return (domain != null) ? value.substring(domain.length() + 1) : value;
    }

    private static String extractDigest(String path) {
        int digestSplit = path.indexOf("@");
        if (digestSplit != -1) {
            String remainder = path.substring(digestSplit + 1);
            Matcher matcher = Regex.DIGEST.matcher(remainder);
            if (matcher.find()) {
                String digest = remainder.substring(0, matcher.end());
                String remainderPath = remainder.substring(matcher.end());
                return path.substring(0, digestSplit) + remainderPath;
            }
        }
        return null;
    }

    private static String extractTag(String path) {
        int tagSplit = path.lastIndexOf(":");
        if (tagSplit != -1) {
            String remainder = path.substring(tagSplit + 1);
            Matcher matcher = Regex.TAG.matcher(remainder);
            if (matcher.find()) {
                String tag = remainder.substring(0, matcher.end());
                String remainderPath = remainder.substring(matcher.end());
                return path.substring(0, tagSplit) + remainderPath;
            }
        }
        return null;
    }
}
