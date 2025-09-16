class A {
    void m(){
        int i= 0;
        class B {
            int j;
            int i=1;
            void k(){
                j= i;
            }
        }

        class C {
            B b;
        }
    };
}
