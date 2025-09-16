class A {
    void m(Object m) {
        System.out.println("A");
    }
}

class B extends A {
    void k() {
        String s=null;
        m(s);
    }

    private static void m(String s) {
        System.out.println(s);
    }

    void f() {
        m("1");
    }
}