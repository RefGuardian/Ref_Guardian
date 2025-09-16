public class test35 {
    public static void main(String[] args) {
        IntStream.range(0, myAtomicFormulas.length)
                .parallel()
                .forEach(i -> enumerateWithMax(i, myM, myK, formula -> {
                    result.add(formula);
                }));
    }
}