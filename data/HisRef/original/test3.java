class test3{
    Object x = null;

    void m() {
        Object o;

        if (x != null)
            o = x.toString();
        else
            o = "abc";
    }
}