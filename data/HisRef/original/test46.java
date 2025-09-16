class A {
    void m(){
        int i= 0;
        class B {
            int j;
            void k(){
                j= i;
            }
        }
        class C {
            B b;
            int i=1;
        }
    };
}