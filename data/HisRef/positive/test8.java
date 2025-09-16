public class test8 {
    static String[]field;
    public static void main(String[]args){
        int res = field.hashCode() + (field = args).length;
    }
}