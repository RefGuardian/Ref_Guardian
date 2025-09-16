class A {
    void m(){
        int i= 0;
        class B{
            //rename j to i;
            int i;
            void k(){
                i= i;
            }
        }
    };
}