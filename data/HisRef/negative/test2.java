public class test2 {
    public static void main(String[] args) {
        Function<Integer, Integer> fact = n -> n == 0? 1 : n * fact.apply(n-1);
    }
}