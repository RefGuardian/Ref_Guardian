class test3{
    Object x = null;

    void m() {
        Object o;

        o = x != null ? x.toString() : "abc";
    }
}