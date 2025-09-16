public class test186 {

    @Override
    public void run() {
        logDebug("Watch thread started");
        Set<Runnable> actions = new HashSet<>();
        while (this.running) {
            try {
                long timeout = FileWatcher.this.getQuietPeriod().toMillis();
                WatchKey key = this.watchService.poll(timeout, TimeUnit.MILLISECONDS);
                if (key == null) {
                    actions.forEach(this::runSafely);
                    actions.clear();
                }
                else {
                    accumulate(key, actions);
                    key.reset();
                }
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            catch (ClosedWatchServiceException ex) {
                logDebug("File watcher has been closed");
                this.running = false;
            }
        }
        logDebug("Watch thread stopped");
    }

    private void logDebug(String message) {
        logger.debug(message);
    }
}
