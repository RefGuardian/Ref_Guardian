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
    		if (this.stack.isEmpty()) {
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

    		handleValue(value);

    		return this;
    	}

    	/**
    	 * Handles the different value types.
    	 * @param value the value to handle
    	 */
    	private void handleValue(Object value) {
    		if (value == null || value instanceof Boolean || value == JSONObject.NULL) {
    			this.out.append(value);
    
    		}
    		else if (value instanceof Number) {
    			this.out.append(JSONObject.numberToString((Number) value));
    
    		}
    		else {
    			string(value.toString());
    		}
    	}
}
