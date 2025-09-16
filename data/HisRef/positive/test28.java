public class test28 {
    public static void main(String[] args) {
        int x = 10;
        if (args.length == 0) {
            x = 0;
        }
        extracted(args[0], x);
    }

    private static void extracted(String arg0, int x) {
        if (x > 0) {
            System.out.println(arg0);
        } else {
            System.out.println(0);
        }
    }
}