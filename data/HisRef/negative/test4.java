public class test4 {
    static void goo(char c) {
        System.out.println("char");
    }
    static void goo(int i) {
        System.out.println("int");
    }
    public static void main(String[] args) {
        goo((int) 'a');
    }
}