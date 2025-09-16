public class test226 {

    private List<HttpMessageConverter<?>> getCombinedConverters(Collection<HttpMessageConverter<?>> converters,
                                   List<HttpMessageConverter<?>> defaultConverters) {
        List<HttpMessageConverter<?>> combined = new ArrayList<>();
        List<HttpMessageConverter<?>> processing = new ArrayList<>(converters);
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
                configurePartConverters(allEncompassingConverter, converters);
            }
        }
        combined.addAll(0, processing);
        return combined;
    }

    private boolean isReplacement(HttpMessageConverter<?> defaultConverter, HttpMessageConverter<?> candidate) {
        // extract the method implementation here
    }

    private void configurePartConverters(AllEncompassingFormHttpMessageConverter allEncompassingConverter, Collection<HttpMessageConverter<?>> converters) {
        // extract the method implementation here
    }
}
