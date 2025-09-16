public class test186 {

    private static final Logger logger = LoggerFactory.getLogger(test186.class);

    @Override
    public void run() {
        logger.debug("Watch thread started");
        Set<Runnable> actions = new HashSet<>();
        while (this.running) {
            try {
                long timeout = FileWatcher.this.quietPeriod.toMillis();
                WatchKey key = this.watchService.poll(timeout, TimeUnit.MILLISECONDS);
                if (key == null) {
                    actions.forEach(this::runSafely);
                    actions.clear();
                } else {
                    accumulate(key, actions);
                    key.reset();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (ClosedWatchServiceException ex) {
                logger.debug("File watcher has been closed");
                this.running = false;
            }
        }
        logger.debug("Watch thread stopped");
    }
}
