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
        foo(1);
        super.foo(n);
    }
}