public class test8 {
    static String[]field;
    public static void main(String[]args){
        int res = add((field=args).length,field.hashCode());
    }
    static int add(int x, int y){
        return y+x;
    }
}