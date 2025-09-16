public class test15 {

    @Override
    public SpringApplicationRunListener getRunListener(SpringApplication application) {
        return new SpringApplicationRunListener() {

            @Override
            public void starting(ConfigurableBootstrapContext bootstrapContext) {
                extractMethod(bootstrapContext, application);
            }

            @Override
            public void contextLoaded(ConfigurableApplicationContext context) {
                extractMethod(context);
            }

            @Override
            public void failed(ConfigurableApplicationContext context, Throwable exception) {
                ContextLoaderHook.this.failedContexts.add(context);
            }

            private void extractMethod(ConfigurableBootstrapContext bootstrapContext, SpringApplication application) {
                ContextLoaderHook.this.configurer.accept(application);
                if (ContextLoaderHook.this.mode == Mode.AOT_RUNTIME) {
                    application.addInitializers(
                            (AotApplicationContextInitializer<?>) ContextLoaderHook.this.initializer::initialize);
                }
            }

            private void extractMethod(ConfigurableApplicationContext context) {
                ContextLoaderHook.this.contexts.add(context);
                if (ContextLoaderHook.this.mode == Mode.AOT_PROCESSING) {
                    throw new AbandonedRunException(context);
                }
            }
        };
    }
}
