public class test290 {

    private void string(String value) {
    		this.out.append("\"");
    		for (int i = 0, length = value.length(); i < length; i++) {
    			char c = value.charAt(i);
    
    			/*
    			 * From RFC 4627, "All Unicode characters may be placed within the quotation
    			 * marks except for the characters that must be escaped: quotation mark,
    			 * reverse solidus, and the control characters (U+0000 through U+001F)."
    			 */
    			switch (c) {
    				case '"', '\\', '/' -> this.out.append('\\').append(c);
    				case '\t' -> this.out.append("\\t");
    				case '\b' -> this.out.append("\\b");
    				case '\n' -> this.out.append("\\n");
    				case '\r' -> this.out.append("\\r");
    				case '\f' -> this.out.append("\\f");
    				default -> {
    					if (c <= 0x1F) {
    						this.out.append(String.format("\\u%04x", (int) c));
    					}
    					else {
    						this.out.append(c);
    					}
    				}
    			}
    
    		}
    		this.out.append("\"");
    	}
}
