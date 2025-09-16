package testProject;
public class Main {
    static String[]field;
    public static void main(String[]args) {
        int x = (field = args).length;
        int y = field.hashCode();
        int res = y + x;
    }
}