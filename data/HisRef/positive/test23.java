class A {
    protected final int b = 1;
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