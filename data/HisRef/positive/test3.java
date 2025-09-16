class test3{
    Object x = null;

    void m() {
        Object o = x.toString();

        if (x != null) {}
        else
            o = "abc";
    }
}