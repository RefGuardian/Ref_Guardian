public class test226 {

    private List<HttpMessageConverter<?>> getCombinedConverters(Collection<HttpMessageConverter<?>> converters,
    			List<HttpMessageConverter<?>> defaultConverters) {
    		List<HttpMessageConverter<?>> processing = new ArrayList<>(converters);
    		List<HttpMessageConverter<?>> combined = extractCombinedConverters(defaultConverters, processing);
    		return combined;
    	}

    private List<HttpMessageConverter<?>> extractCombinedConverters(List<HttpMessageConverter<?>> defaultConverters, List<HttpMessageConverter<?>> processing) {
        List<HttpMessageConverter<?>> combined = new ArrayList<>();
        for (HttpMessageConverter<?> defaultConverter : defaultConverters) {
            Iterator<HttpMessageConverter<?>> iterator = processing.iterator();
            while (iterator.hasNext()) {
                HttpMessageConverter<?> candidate = iterator.next();
                if (isReplacement(defaultConverter, candidate)) {
                    combined.add(candidate);
                    iterator.remove();
                }
            }
            combined.add(defaultConverter);
            if (defaultConverter instanceof AllEncompassingFormHttpMessageConverter allEncompassingConverter) {
                configurePartConverters(allEncompassingConverter, processing);
            }
        }
        combined.addAll(0, processing);
        return combined;
    }
}
