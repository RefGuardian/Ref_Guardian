public class test294 {

    /**
     * Returns the string up to but not including {@code quote}, unescaping any character
     * escape sequences encountered along the way. The opening quote should have already
     * been read. This consumes the closing quote, but does not include it in the returned
     * string.
     * @param quote either ' or ".
     * @return the string up to but not including {@code quote}
     * @throws NumberFormatException if any unicode escape sequences are malformed.
     * @throws JSONException if processing of json failed
     */
    public String nextString(char quote) throws JSONException {
        /*
         * For strings that are free of escape sequences, we can just extract the result
         * as a substring of the input. But if we encounter an escape sequence, we need to
         * use a StringBuilder to compose the result.
         */
        StringBuilder builder = null;

        /* the index of the first character not yet appended to the builder. */
        int start = this.pos;

        while (this.pos < this.in.length()) {
            int c = this.in.charAt(this.pos++);
            if (c == quote) {
                if (builder == null) {
                    // a new string avoids leaking memory
                    return new String(this.in.substring(start, this.pos - 1));
                } else {
                    builder.append(this.in, start, this.pos - 1);
                    return builder.toString();
                }
            }

            if (c == '\\') {
                if (this.pos == this.in.length()) {
                    throw syntaxError("Unterminated escape sequence");
                }
                if (builder == null) {
                    builder = new StringBuilder();
                }
                builder.append(this.in, start, this.pos - 1);
                builder.append(readEscapeCharacter());
                start = this.pos;
            }
        }

        throw syntaxError("Unterminated string");
    }

    /**
     * Reads the escape character and returns the character it represents.
     *
     * @return the character represented by the escape character
     */
    private char readEscapeCharacter() {
        if (this.pos == this.in.length()) {
            throw syntaxError("Unterminated escape sequence");
        }
        char escaped = this.in.charAt(this.pos++);
        switch (escaped) {
            case 'b':
                return '\b';
            case 't':
                return '\t';
            case 'n':
                return '\n';
            case 'f':
                return '\f';
            case 'r':
                return '\r';
            case 'u':
                if (this.pos + 4 > this.in.length()) {
                    throw syntaxError("Invalid unicode escape sequence");
                }
                String hex = this.in.substring(this.pos, this.pos + 4);
                this.pos += 4;
                try {
                    return (char) Integer.parseInt(hex, 16);
                } catch (NumberFormatException e) {
                    throw syntaxError("Invalid unicode escape sequence");
                }
            default:
                return escaped;
        }
    }
}
