public class test183 {

    private static final long START_TIME = System.nanoTime();

    List<String> filter(List<String> configurations) {
        String[] candidates = StringUtils.toStringArray(configurations);
        boolean skipped = false;
        for (AutoConfigurationImportFilter filter : this.filters) {
            boolean[] match = filter.match(candidates, this.autoConfigurationMetadata);
            for (int i = 0; i < match.length; i++) {
                if (!match[i]) {
                    candidates[i] = null;
                    skipped = true;
                }
            }
        }
        if (!skipped) {
            return configurations;
        }
        List<String> result = new ArrayList<>(candidates.length);
        for (String candidate : candidates) {
            if (candidate != null) {
                result.add(candidate);
            }
        }
        if (logger.isTraceEnabled()) {
            int numberFiltered = configurations.size() - result.size();
            logger.trace("Filtered " + numberFiltered + " auto configuration class in "
                    + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - START_TIME) + " ms");
        }
        return result;
    }
}
