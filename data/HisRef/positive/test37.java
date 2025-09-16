public class test37 {
    public static void main(String[] args) {
        boolean positiveArray[] = new boolean[3];
        Arrays.fill(positiveArray, true);

        for (boolean value : positiveArray) {
            System.out.println("Value = " + !value);
        }
    }
}