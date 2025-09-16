public class test240 {

    private void performPreinitialization() {
        initializeThread();
    }

    private void initializeThread() {
        Thread thread = createThread();
        thread.start();
    }

    private Thread createThread() {
        return new Thread(new Runnable() {

            @Override
            public void run() {
                runSafely(new ConversionServiceInitializer());
                runSafely(new ValidationInitializer());
                if (!runSafely(new MessageConverterInitializer())) {
                    // If the MessageConverterInitializer fails to run, we still might
                    // be able to
                    // initialize Jackson
                    runSafely(new JacksonInitializer());
                }
                runSafely(new CharsetInitializer());
                runSafely(new TomcatInitializer());
                runSafely(new JdkInitializer());
                preinitializationComplete.countDown();
            }

            boolean runSafely(Runnable runnable) {
                try {
                    runnable.run();
                    return true;
                } catch (Throwable ex) {
                    return false;
                }
            }

        }, "background-preinit");
    }

    private void runSafely(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable ex) {
        }
    }

}
