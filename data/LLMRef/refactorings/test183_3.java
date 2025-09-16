public class test183 {

    List<String> filter(List<String> configurations) {
		long startTime = System.nanoTime();
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
		logConfigurationFiltering(startTime, configurations.size(), result.size());
		return result;
	}

	private void logConfigurationFiltering(long startTime, int configurationsSize, int resultSize) {
		if (logger.isTraceEnabled()) {
			int numberFiltered = configurationsSize - resultSize;
			logger.trace("Filtered " + numberFiltered + " auto configuration class in "
					+ TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");
		}
	}
}
