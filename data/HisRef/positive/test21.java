public class test21 extends JComponent {

    private void f() {
        extracted();
    }

    private static void extracted() {
        Color c = Color.RED;
        setBackground(c);
    }

    private class B extends JComponent {
        void g() {
            extracted();
        }
    }
}