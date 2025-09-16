public class test12 {
    public void testLoop(){
        int x = 0;
        for (int i = x; i < 10; i++)
            extracted(x, i);
    }

    private void extracted(int x, int i) {
        assertEquals(i,x++);
    }
}