public class test11{
    private int getGridY(int y) {
        int tmp = 0, res = 1;
        while (res < y) {
            //extract here
            tmp = res;
            res += 1;
            //to here
        }
        return tmp;
    }
}