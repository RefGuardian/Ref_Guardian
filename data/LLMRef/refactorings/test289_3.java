public class test289 {

    /**
	 * Encodes {@code value}.
	 * @param value a {@link JSONObject}, {@link JSONArray}, String, Boolean, Integer,
	 * Long, Double or null. May not be {@link Double#isNaN() NaNs} or
	 * {@link Double#isInfinite() infinities}.
	 * @return this stringer.
	 * @throws JSONException if processing of json failed
	 */
	public JSONStringer value(Object value) throws JSONException {
		if (isEmptyStack()) {
			throw new JSONException("Nesting problem");
		}

		if (value instanceof JSONArray) {
			((JSONArray) value).writeTo(this);
			return this;
		}
		else if (value instanceof JSONObject) {
			((JSONObject) value).writeTo(this);
			return this;
		}

		beforeValue();

		if (value == null || value instanceof Boolean || value == JSONObject.NULL) {
			appendValue(value);

		}
		else if (value instanceof Number) {
			appendValue(JSONObject.numberToString((Number) value));

		}
		else {
			string(value.toString());
		}

		return this;
	}

	private boolean isEmptyStack() {
		return this.stack.isEmpty();
	}

	private void appendValue(Object value) {
		this.out.append(value);
	}

	private void appendValue(String value) {
		this.out.append(value);
	}
}
