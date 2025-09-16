public class test30 {
    static void foo(int i, boolean d) {
        System.out.println(!d);
        if (i > 0) {
            foo(i - 1, !d);  // <-- note wrong negation here
        }
    }

    public static void main(String[] args) {
        foo(3, false);
    }
}