public class test293 {

    private int nextCleanInternal() throws JSONException {
        while (this.pos < this.in.length()) {
            int c = this.in.charAt(this.pos++);
            switch (c) {
                case '\t', ' ', '\n', '\r':
                    continue;

                case '/':
                    if (this.pos == this.in.length()) {
                        return c;
                    }

                    char peek = this.in.charAt(this.pos);
                    switch (peek) {
                        case '*':
                            // skip a /* c-style comment */
                            this.pos++;
                            int commentEnd = this.in.indexOf("*/", this.pos);
                            if (commentEnd == -1) {
                                throw syntaxError("Unterminated comment");
                            }
                            this.pos = commentEnd + 2;
                            continue;

                        case '/':
                            // skip a // end-of-line comment
                            this.pos++;
                            skipToEndOfLine();
                            continue;

                        default:
                            return c;
                    }

                case '#':
                    /*
                     * Skip a # hash end-of-line comment. The JSON RFC doesn't specify
                     * this behavior, but it's required to parse existing documents. See
                     * https://b/2571423.
                     */
                    skipToEndOfLine();
                    continue;

                default:
                    return c;
            }
        }

        return -1;
    }
}
