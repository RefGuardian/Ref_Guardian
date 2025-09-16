import java.util.List;

public class test33 {
    int a = 1;
    public List<Pojo> things;

    void fool() {
        while(a == 1) {
            Pojo x = getPojo();
            if (x == null) {
                return;   // BUG: THIS SHOULD BE break
            }

            double y = 2 * x.it;
        }
        a = 2;
    }

    private Pojo getPojo() {
        Pojo x = things.get(0); // from here

        if(x.it > 0.5) {
            return null;
        }

        things.remove(x); // to here
        return x;
    }

    static class Pojo {
        double it;
        Pojo(double w) {
            it = w;
        }
    }
}