 public class test10 {
    void f(int p){
        new Ad(){
            void f(){
                int u= Ad.this.hashCode();
            }
        };
    }
}