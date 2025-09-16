public class test17 {
    String[] Lines;
    int i = 0;
    private static void inc() {
        i++;
    }
    public static void main(String[] args) {
       log.warn(Lines[i]);
       log.warn(Lines[i]);
       inc();
       log.warn(Lines[i]);
    }
}