public class test5
{
    static final void testEclipseInlineFailure(int x1, int x2)
    {
        final int dx = x2 - x1;
        System.out.println(dx);
    }
    public static void main(String[] args)
    {
        testEclipseInlineFailure(3 + 4, 5);
    }

}
