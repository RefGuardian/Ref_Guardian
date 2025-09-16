public class test16 {

    private ApplicationContext run(ThrowingSupplier<ConfigurableApplicationContext> action) throws Exception {
        try {
            return runApplicationContext(action);
        } catch (Exception ex) {
            handleExceptions(ex);
        }
        List<ApplicationContext> rootContexts = this.getContexts();
        Assert.state(!rootContexts.isEmpty(), "No root application context located");
        Assert.state(rootContexts.size() == 1, "No unique root application context located");
        return rootContexts.get(0);
    }

    private ApplicationContext runApplicationContext(ThrowingSupplier<ConfigurableApplicationContext> action) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.withHook(this, action);
        if (context != null) {
            return context;
        }
        return checkFailedContexts();
    }

    private void handleExceptions(Exception ex) throws ContextLoadException {
        if (this.getFailedContextsSize() == 1) {
            throw new ContextLoadException(this.failedContexts.get(0), ex);
        }
        throw ex;
    }

    private List<ApplicationContext> getContexts() {
        return this.contexts.stream()
                .filter((context) -> context.getParent() == null)
                .toList();
    }

    private ApplicationContext checkFailedContexts() {
        List<ApplicationContext> rootContexts = this.getContexts();
        Assert.state(!rootContexts.isEmpty(), "No root application context located");
        Assert.state(rootContexts.size() == 1, "No unique root application context located");
        return rootContexts.get(0);
    }

    private int getFailedContextsSize() {
        return this.failedContexts.size();
    }
}
