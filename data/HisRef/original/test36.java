public class test36 {
    String bar() {
        return "bar";
    }

    String baz(boolean condition) {
        if (condition)
            return bar();

        return "default";
    }
}