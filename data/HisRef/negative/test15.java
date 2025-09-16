class test15 {
    private boolean flag = false;

    public void callerMethod() throws InterruptedException {
        // Some logic here
        synchronized (this) {
            flag = true;
            notify();
        }
    }
}