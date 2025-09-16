public class test4 {
    static void goo(char c) {
        System.out.println("char");
    }
    static void goo(int i) {
        System.out.println("int");
    }
    static int foo() {
        return 'a';
    }
    public static void main(String[] args) {
        goo(foo());
    }
}