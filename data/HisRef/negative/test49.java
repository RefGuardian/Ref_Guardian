class A {
    void m(Object m) {
        System.out.println("A");
    }
}

class B extends A {
    void k() {
        String s=null;
        System.out.println(s);
    }

    void f() {
        m("1");
    }
}