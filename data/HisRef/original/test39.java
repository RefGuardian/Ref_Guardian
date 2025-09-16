public class test39 {
    public static void main(String[] args) {
        FileFilter java = f -> f.getName().endsWith(".java");
        java.getClass();
    }
}