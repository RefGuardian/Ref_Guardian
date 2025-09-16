class test57 {
    // change method signature 'm' to 'k'
    public void k(){
        System.out.println("m");
    }
    class B{
        void k(){
            k();
        }
    }
}