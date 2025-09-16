class A {
    void m(Object m) {
        System.out.println("A");
    }
    // inline method 'test()'
    void test() {
        m("1");
    }
}

class B extends A {
    void m(Object m) {
        System.out.println("B");
    }

    void f() {
        test();
    }
}