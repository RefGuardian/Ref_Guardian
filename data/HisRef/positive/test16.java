public class test16 {
    public static void main(String[] args) {
        Object s = "Hi";

        Number number;
        number = s instanceof String ? Long.parseLong("1") : Double.parseDouble("1");

        System.out.println(number);
    }
}