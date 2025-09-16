public class test297 {

	/**
	 * Reads a sequence of values and the trailing closing brace ']' of an array. The
	 * opening brace '[' should have already been read. Note that "[]" yields an empty
	 * array, but "[,]" returns a two-element array equivalent to "[null,null]".
	 * 
	 * @return an array
	 * @throws JSONException if processing of json failed
	 */
	private JSONArray readArray() throws JSONException {
		JSONArray result = new JSONArray();
		boolean hasTrailingSeparator = false;
		while (true) {
			switch (nextCleanInternal()) {
				case -1:
					throw syntaxError("Unterminated array");
				case ']':
					if (hasTrailingSeparator) {
						result.put(null);
					}
					return result;
				case ',', ';':
					result.put(null);
					hasTrailingSeparator = true;
					continue;
				default:
					this.pos--;
			}
			result.put(nextValue());
			switch (nextCleanInternal()) {
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

	/**
	 * Returns the next clean internal value.
	 * 
	 * @return the next clean internal value
	 */
	private int nextCleanInternal() {
		// Implementation of nextCleanInternal method
		return 0;
	}
}
