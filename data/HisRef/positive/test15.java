class test15 {
    private boolean flag = false;

    public void callerMethod() throws InterruptedException {
        flag = true;
        notify();
    }
}