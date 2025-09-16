abstract class BaseClass {
    public static final int VALUE = 1;
}
class OtherClass{
    public static final int VALUE = 1;
}
public class test19 {
    public static void main(String[] args) {
        System.out.println(OtherClass.VALUE);
    }
}