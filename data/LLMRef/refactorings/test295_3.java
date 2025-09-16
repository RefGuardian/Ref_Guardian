public class test295 {

    /**
    	 * Unescapes the character identified by the character or characters that immediately
    	 * follow a backslash. The backslash '\' should have already been read. This supports
    	 * both unicode escapes "u000A" and two-character escapes "\n".
    	 * @return the unescaped char
    	 * @throws NumberFormatException if any unicode escape sequences are malformed.
    	 * @throws JSONException if processing of json failed
    	 */
    	private char readEscapedChar() throws JSONException {
    		char escaped = this.in.charAt(this.pos++);
    		switch (escaped) {
    			case 'u':
    				if (this.pos + 4 > this.in.length()) {
    					throw syntaxError("Unterminated escape sequence");
    				}
    				String hex = this.in.substring(this.pos, this.pos + 4);
    				this.pos += 4;
    				return (char) Integer.parseInt(hex, 16);
    
    			case 't':
    				return '\t';
    
    			case 'b':
    				return '\b';
    
    			case 'n':
    				return '\n';
    
    			case 'r':
    				return '\r';
    
    			case 'f':
    				return '\f';
    
    			case '\'', '"', '\\':
    			default:
    				return escaped;
    		}
    	}
}
