public class test297 {

	/**
	 * Reads a sequence of values and the trailing closing brace ']' of an array. The
	 * opening brace '[' should have already been read. Note that "[]" yields an empty
	 * array, but "[,]" returns a two-element array equivalent to "[null,null]".
	 * @return an array
	 * @throws JSONException if processing of json failed
	 */
	private JSONArray readArray() throws JSONException {
		JSONArray result = new JSONArray();

		/* to cover input that ends with ",]". */
		boolean hasTrailingSeparator = false;
		int next = nextCleanInternal();

		while (true) {
			switch (next) {
				case -1:
					throw syntaxError("Unterminated array");
				case ']':
					if (hasTrailingSeparator) {
						result.put(null);
					}
					return result;
				case ',', ';':
					/* A separator without a value first means "null". */
					result.put(null);
					hasTrailingSeparator = true;
					next = nextCleanInternal();
					continue;
				default:
					this.pos--;
			}

			result.put(nextValue());
			next = nextCleanInternal();

			switch (next) {
				case ']':
					return result;
				case ',', ';':
					hasTrailingSeparator = true;
					continue;
				default:
					throw syntaxError("Unterminated array");
			}
		}
	}
}
