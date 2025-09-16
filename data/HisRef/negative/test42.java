public class test42 {

    public int x;

    public class Inner {
        public int x;

        public void foo() {
            x = test42.this.x + 1;
        }
    }
}