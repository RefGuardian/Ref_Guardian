public class test16 {
    public static void main(String[] args) {
        Object s = "Hi";

        Number number;
        if(s instanceof String) {
            number = Long.parseLong("1");
        }
        else {
            number = Double.parseDouble("1");
        }

        System.out.println(number);
    }
}