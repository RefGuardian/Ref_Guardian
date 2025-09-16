public class test299 {

    /**
    	 * Encodes the number as a JSON string.
    	 * @param number a finite value. May not be {@link Double#isNaN() NaNs} or
    	 * {@link Double#isInfinite() infinities}.
    	 * @return the encoded value
    	 * @throws JSONException if an error occurs
    	 */
    	public static String numberToString(Number number) throws JSONException {
    		if (number == null) {
    			throw new JSONException("Number must be non-null");
    		}
    
    		double doubleValue = number.doubleValue();
    		JSON.checkDouble(doubleValue);
    
    		// the original returns "-0" instead of "-0.0" for negative zero
    		if (number.equals(NEGATIVE_ZERO)) {
    			return "-0";
    		}
    
    		long longValue = number.longValue();
    		if (doubleValue == longValue) {
    			return Long.toString(longValue);
    		}
    
    		return number.toString();
    	}
}
