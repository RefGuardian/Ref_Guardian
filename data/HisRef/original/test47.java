class A extends C {
}

class B {
    void main() {
        final int b = 2;
        new A() {
            void m() {
                System.out.println(b);
            }
        };
    }
}
class C {
    protected final int b = 1;
}