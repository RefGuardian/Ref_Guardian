public class test2 {
    public static void main(String[] args) {
        Function<Integer, Integer> fact = new Function<Integer, Integer>() {
            // Super important comment

            public Integer apply(Integer n) {
                return n == 0? 1 : n * fact(n-1);
            }
        };
    }
}