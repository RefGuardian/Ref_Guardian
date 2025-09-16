public class test12 {
    public void testLoop(){
        int x = 0;
        for (int i = x; i < 10; i++)
            assertEquals(i,x++);
    }
}