class A {
    void m(Object m) {
        System.out.println("A");
    }
}

class B extends A {
    void k(String m) {
        System.out.println("B");
    }

    void f() {
        m("1");
    }

    // pull up ‘m’
    void m(String m) {
        System.out.println("B");
    }
}

class C extends B{
}