public class test18 {
    String[] lines;
    int i = 0;
    private String foo(String mes) {
        return mes + ": " + lines[i++];
    }

    public static void main(String[] args) {
        log.warn(foo("test"));
        log.warn(foo("test"));
    }
}