public class test10 {

    @SuppressWarnings("rawtypes")
    private Object mockArgument(Class<?> type) throws Exception {
        if (String.class.equals(type)) {
            return "String";
        }
        if (Object[].class.equals(type)) {
            return new Object[0];
        }
        if (URI.class.equals(type)) {
            return new URI("http://localhost");
        }
        if (HttpMethod.class.equals(type)) {
            return HttpMethod.GET;
        }
        if (Class.class.equals(type)) {
            return Object.class;
        }
        if (RequestEntity.class.equals(type)) {
            return new RequestEntity(HttpMethod.GET, new URI("http://localhost"));
        }
        return mock(type);
    }
}
