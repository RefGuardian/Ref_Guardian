public class test9 {
    private static class Twelve implements Runnable {
        private final int fFive;

        private final int fSeven;

        private Twelve(int five, int seven) {
            super();
            fFive = five;
            fSeven = seven;
        }

        public void run() {
            System.out.println("The answer is " + (fFive * fSeven));
        }
    }

    private static final int SEVEN = 7;
    private static final int FIVE = 5;

    public static void main(String[] args) {
        Runnable runnable = new Twelve(FIVE, SEVEN);
        runnable.run();
    }
}
