public class test290 {

    private void string(String value) {
        this.out.append(escapeString(value));
    }
    
    private String escapeString(String value) {
        StringBuilder escapedValue = new StringBuilder("\"");
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);
            
            switch (c) {
                case '"', '\\', '/' -> escapedValue.append('\\').append(c);
                case '\t' -> escapedValue.append("\\t");
                case '\b' -> escapedValue.append("\\b");
                case '\n' -> escapedValue.append("\\n");
                case '\r' -> escapedValue.append("\\r");
                case '\f' -> escapedValue.append("\\f");
                default -> {
                    if (c <= 0x1F) {
                        escapedValue.append(String.format("\\u%04x", (int) c));
                    } else {
                        escapedValue.append(c);
                    }
                }
            }
        }
        return escapedValue.append("\"").toString();
    }
}
