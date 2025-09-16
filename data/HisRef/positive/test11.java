public class test11{
    private int getGridY(int y) {
        int tmp = 0, res = 1;
        while (res < y) {
            res = extracted(res);
        }
        return tmp;
    }
    void extracted(int res) {
        tmp = res;
        res += 1;
    }
}