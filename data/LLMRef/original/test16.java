public class test16 {

    private ApplicationContext run(ThrowingSupplier<ConfigurableApplicationContext> action) throws Exception {
    			try {
    				ConfigurableApplicationContext context = SpringApplication.withHook(this, action);
    				if (context != null) {
    					return context;
    				}
    			}
    			catch (AbandonedRunException ex) {
    				// Ignore
    			}
    			catch (Exception ex) {
    				if (this.failedContexts.size() == 1) {
    					throw new ContextLoadException(this.failedContexts.get(0), ex);
    				}
    				throw ex;
    			}
    			List<ApplicationContext> rootContexts = this.contexts.stream()
    				.filter((context) -> context.getParent() == null)
    				.toList();
    			Assert.state(!rootContexts.isEmpty(), "No root application context located");
    			Assert.state(rootContexts.size() == 1, "No unique root application context located");
    			return rootContexts.get(0);
    		}
}
