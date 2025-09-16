public class test2 {

    @Override
    		public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
    			RestTemplate rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.custom()
    				.setRetryStrategy(new DefaultHttpRequestRetryStrategy(10, TimeValue.of(1, TimeUnit.SECONDS)))
    				.build()));
    			rest.setErrorHandler(new NoOpResponseErrorHandler());
    			rest.setUriTemplateHandler(new UriTemplateHandler() {
    
    				@Override
    				public URI expand(String uriTemplate, Object... uriVariables) {
    					return URI.create("http://localhost:" + RestTemplateParameterResolver.this.launcher.getHttpPort()
    							+ uriTemplate);
    				}
    
    				@Override
    				public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
    					return URI.create("http://localhost:" + RestTemplateParameterResolver.this.launcher.getHttpPort()
    							+ uriTemplate);
    				}
    
    			});
    			return rest;
    		}
}
