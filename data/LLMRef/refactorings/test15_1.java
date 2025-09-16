public class test15 {

    private static final Mode MODE = ContextLoaderHook.this.mode;

    @Override
    public SpringApplicationRunListener getRunListener(SpringApplication application) {
        return new SpringApplicationRunListener() {

            @Override
            public void starting(ConfigurableBootstrapContext bootstrapContext) {
                ContextLoaderHook.this.configurer.accept(application);
                if (MODE == Mode.AOT_RUNTIME) {
                    application.addInitializers((AotApplicationContextInitializer<?>) ContextLoaderHook.this.initializer::initialize);
                }
            }

            @Override
            public void contextLoaded(ConfigurableApplicationContext context) {
                ContextLoaderHook.this.contexts.add(context);
                if (MODE == Mode.AOT_PROCESSING) {
                    throw new AbandonedRunException(context);
                }
            }

            @Override
            public void failed(ConfigurableApplicationContext context, Throwable exception) {
                ContextLoaderHook.this.failedContexts.add(context);
            }

        };
    }
}
