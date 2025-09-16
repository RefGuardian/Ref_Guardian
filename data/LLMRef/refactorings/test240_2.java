public class test240 {

    private void performPreinitialization() {
        initThread();
    }

    private void initThread() {
        Thread thread = new Thread(new BackgroundInitializer(), "background-preinit");
        thread.start();
    }

    private class BackgroundInitializer implements Runnable {

        @Override
        public void run() {
            initializeServices();
            preinitializationComplete.countDown();
        }

        private void initializeServices() {
            runSafely(new ConversionServiceInitializer());
            runSafely(new ValidationInitializer());
            if (!runSafely(new MessageConverterInitializer())) {
                runSafely(new JacksonInitializer());
            }
            runSafely(new CharsetInitializer());
            runSafely(new TomcatInitializer());
            runSafely(new JdkInitializer());
        }
    }

    boolean runSafely(Runnable runnable) {
        try {
            runnable.run();
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

}
