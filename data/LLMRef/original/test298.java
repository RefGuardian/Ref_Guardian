public class test298 {

    /**
    	 * Appends {@code value} to the array already mapped to {@code name}. If this object
    	 * has no mapping for {@code name}, this inserts a new mapping. If the mapping exists
    	 * but its value is not an array, the existing and new values are inserted in order
    	 * into a new array which is itself mapped to {@code name}. In aggregate, this allows
    	 * values to be added to a mapping one at a time.
    	 * @param name the name of the property
    	 * @param value a {@link JSONObject}, {@link JSONArray}, String, Boolean, Integer,
    	 * Long, Double, {@link #NULL} or null. May not be {@link Double#isNaN() NaNs} or
    	 * {@link Double#isInfinite() infinities}.
    	 * @return this object.
    	 * @throws JSONException if an error occurs
    	 */
    	public JSONObject accumulate(String name, Object value) throws JSONException {
    		Object current = this.nameValuePairs.get(checkName(name));
    		if (current == null) {
    			return put(name, value);
    		}
    
    		// check in accumulate, since array.put(Object) doesn't do any checking
    		if (value instanceof Number) {
    			JSON.checkDouble(((Number) value).doubleValue());
    		}
    
    		if (current instanceof JSONArray array) {
    			array.put(value);
    		}
    		else {
    			JSONArray array = new JSONArray();
    			array.put(current);
    			array.put(value);
    			this.nameValuePairs.put(name, array);
    		}
    		return this;
    	}
}
