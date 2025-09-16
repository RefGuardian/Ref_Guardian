public class test35 {
    public static void main(String[] args) {
        IntStream.range(0, myAtomicFormulas.length)
                .parallel()
                .forEach(i -> enumerateWithMax(i, myM, myK, new Consumer<Formula>() {
                    @Override
                    public synchronized void accept(final Formula formula) {
                        result.add(formula);
                    }
                }));
    }
}