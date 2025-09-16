class A {
    public void foo(int n) {

    }

    public void bar() {
        foo(n);
    }
}

class B extends A {
    public void foo(int n) {

    }

    public void bar(int n) {
        foo(n);
        super.foo(n);
    }
}