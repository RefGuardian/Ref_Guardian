public class test12 {
    public void testLoop(){
        int x = 0;
        for (int i = x; i < 10; i++)
            x = extracted(x, i);
    }

    private int extracted(int x, int i) {
        assertEquals(i,x++);
        return x;
    }
}