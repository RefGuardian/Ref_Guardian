public class test240 {

    private void performPreinitialization() {
        try {
            Thread thread = new Thread(new Runnable() {

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

                private void runSafely(Runnable runnable) {
                    try {
                        runnable.run();
                    }
                    catch (Throwable ex) {
                        // handle exception
                    }
                }

            }, "background-preinit");
            thread.start();
        }
        catch (Exception ex) {
            // This will fail on GAE where creating threads is prohibited. We can safely
            // continue but startup will be slightly slower as the initialization will now
            // happen on the main thread.
            preinitializationComplete.countDown();
        }
    }
}
