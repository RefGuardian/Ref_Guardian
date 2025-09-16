public class test28 {
    public static void main(String[] args) {
        int x = 10;
        if (args.length == 0) {
            x = 0;
        }
        if (x > 0) {
            System.out.println(args[0]);
        } else {
            System.out.println(0);
        }
    }
}