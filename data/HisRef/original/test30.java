public class test30 {
    static void foo(int i, boolean b) {
        System.out.println(b);
        if (i > 0) {
            foo(i - 1, b);
        }
    }

    public static void main(String[] args) {
        foo(3, true);
    }
}