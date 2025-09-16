public class test296 {

    /**
     * Reads a sequence of key/value pairs and the trailing closing brace '}' of an
     * object. The opening brace '{' should have already been read.
     *
     * @return an object
     * @throws JSONException if processing of json failed
     */
    private JSONObject readObject() throws JSONException {
        JSONObject result = new JSONObject();

        /* Peek to see if this is the empty object. */
        int first = nextCleanInternal();
        if (first == '}') {
            return result;
        } else if (first != -1) {
            this.pos--;
        }

        while (true) {
            Object name = nextValue();
            if (!(name instanceof String)) {
                if (name == null) {
                    throw syntaxError("Names cannot be null");
                } else {
                    throw syntaxError(
                            "Names must be strings, but " + name + " is of type " + name.getClass().getName());
                }
            }

            /*
             * Expect the name/value separator to be either a colon ':', an equals sign
             * '=', or an arrow "=>". The last two are bogus but we include them because
             * that's what the original implementation did.
             */
            int separator = nextCleanInternal();
            if (separator != ':' && separator != '=') {
                throw syntaxError("Expected ':' after " + name);
            }
            if (this.pos < this.in.length() && this.in.charAt(this.pos) == '>') {
                this.pos++;
            }

            result.put((String) name, nextValue());

            switch (nextCleanInternal()) {
                case '}':
                    return result;
                case ';':
                    case ',':
                    continue;
                default:
                    throw syntaxError("Unterminated object");
            }
        }
    }
}
