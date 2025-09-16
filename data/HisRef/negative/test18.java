public class test18 {
    String[] lines;
    int i = 0;
    private String foo(String mes) {
        return mes + ": " + lines[i++];
    }

    public static void main(String[] args) {
        final String test = foo("test");
        log.warn(test);
        log.warn(foo("test"));
    }
}