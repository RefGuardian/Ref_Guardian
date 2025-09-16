public class test291 {

    /**
     * Inserts any necessary separators and whitespace before a literal value, inline
     * array, or inline object. Also adjusts the stack to expect either a closing bracket
     * or another element.
     * @throws JSONException if processing of json failed
     */
    private void beforeValue() throws JSONException {
        if (this.stack.isEmpty()) {
            return;
        }

        Scope context = peek();
        switch(context){
            case EMPTY_ARRAY: // first in array
                replaceTop(Scope.NONEMPTY_ARRAY);
                newline();
                break;
            case NONEMPTY_ARRAY: // another in array
                this.out.append(',');
                newline();
                break;
            case DANGLING_KEY: // value for key
                this.out.append(this.indent == null ? ":" : ": ");
                replaceTop(Scope.NONEMPTY_OBJECT);
                break;
            default:
                if (context != Scope.NULL) {
                    throw new JSONException("Nesting problem");
                }
        }
    }
}
