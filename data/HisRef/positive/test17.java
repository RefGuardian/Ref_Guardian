public class test17 {
    String[] Lines;
    int i = 0;
    private static void inc() {
        i++;
    }
    public static void main(String[] args) {
        final String line = Lines[i];
        log.warn(line);
       log.warn(line);
       inc();
       log.warn(line);
    }
}