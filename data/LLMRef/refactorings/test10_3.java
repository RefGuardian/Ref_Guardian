public class test10 {

	private static final String DEFAULT_STRING = "String";
	private static final Object[] DEFAULT_OBJECT_ARRAY = new Object[0];
	private static final URI DEFAULT_URI = URI.create("http://localhost");
	private static final HttpMethod DEFAULT_HTTP_METHOD = HttpMethod.GET;
	private static final Class<?> DEFAULT_CLASS = Object.class;

	@SuppressWarnings("rawtypes")
	private Object mockArgument(Class<?> type) throws Exception {
		if (String.class.equals(type)) {
			return DEFAULT_STRING;
		}
		if (Object[].class.equals(type)) {
			return DEFAULT_OBJECT_ARRAY;
		}
		if (URI.class.equals(type)) {
			return DEFAULT_URI;
		}
		if (HttpMethod.class.equals(type)) {
			return DEFAULT_HTTP_METHOD;
		}
		if (Class.class.equals(type)) {
			return DEFAULT_CLASS;
		}
		if (RequestEntity.class.equals(type)) {
			return new RequestEntity(DEFAULT_HTTP_METHOD, DEFAULT_URI);
		}
		return mock(type);
	}
}
