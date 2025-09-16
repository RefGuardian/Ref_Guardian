class ParentClass {
    void f() {
        // extract method, new method name 'method'
        method();
    }

    private static void method() {
        int i;
        System.out.println("parent class");
    }
}

class SourceClass {
    void method() {
        System.out.println("source class");
    }

    class InnerClass extends ParentClass {
        void testMethod() {
            method();
        }
    }
}