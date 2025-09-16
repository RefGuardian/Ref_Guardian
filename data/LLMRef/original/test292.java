public class test292 {

    /**
    	 * Returns the next value from the input.
    	 * @return a {@link JSONObject}, {@link JSONArray}, String, Boolean, Integer, Long,
    	 * Double or {@link JSONObject#NULL}.
    	 * @throws JSONException if the input is malformed.
    	 */
    	public Object nextValue() throws JSONException {
    		int c = nextCleanInternal();
    		switch (c) {
    			case -1:
    				throw syntaxError("End of input");
    
    			case '{':
    				return readObject();
    
    			case '[':
    				return readArray();
    
    			case '\'', '"':
    				return nextString((char) c);
    
    			default:
    				this.pos--;
    				return readLiteral();
    		}
    	}
}
