public class test293 {

    private int nextCleanInternal() throws JSONException {
    	while (this.pos < this.in.length()) {
    		int c = getNextCleanChar();
    		switch (c) {
    			case -1:
    				return -1;
    
    			case '/':
    				return handleForwardSlash();
    
    			case '#':
    				handleHashSign();
    				continue;
    
    			default:
    				return c;
    		}
    	}
    
    	return -1;
    }
    
    private int getNextCleanChar() {
        while (this.pos < this.in.length()) {
            int c = this.in.charAt(this.pos++);
            switch (c) {
                case '\t', ' ', '\n', '\r':
                    continue;
    
                default:
                    return c;
            }
        }
        return -1;
    }
    
    private int handleForwardSlash() throws JSONException {
        if (this.pos == this.in.length()) {
            return '/';
        }
    
        char peek = this.in.charAt(this.pos);
        switch (peek) {
            case '*':
                this.pos++;
                int commentEnd = this.in.indexOf("*/", this.pos);
                if (commentEnd == -1) {
                    throw syntaxError("Unterminated comment");
                }
                this.pos = commentEnd + 2;
                return handleForwardSlash();
    
            case '/':
                this.pos++;
                skipToEndOfLine();
                return handleForwardSlash();
    
            default:
                return '/';
        }
    }
    
    private void handleHashSign() {
        skipToEndOfLine();
    }
    
    private void skipToEndOfLine() {
        // implement skipToEndOfLine function
    }
}
