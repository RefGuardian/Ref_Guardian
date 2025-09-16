public class test44 {
    public void test() {
        int[] arrs = {1,2,3};
        int a = 0;
// extract variable: arrs[a]
        final int arr = arrs[a];
        System.out.println(arr);
        a++;
        System.out.println(arrs[a]);
        a++;
        System.out.println(arrs[a]);
    }
}