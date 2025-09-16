public class test250 {

    private ClientOptions createClientOptions(
    			ObjectProvider<LettuceClientOptionsBuilderCustomizer> clientConfigurationBuilderCustomizers,
    			SslBundle sslBundle) {
    		ClientOptions.Builder builder = initializeClientOptionsBuilder();
    		Duration connectTimeout = getProperties().getConnectTimeout();
    		if (connectTimeout != null) {
    			builder.socketOptions(SocketOptions.builder().connectTimeout(connectTimeout).build());
    		}
    		if (sslBundle != null) {
    			io.lettuce.core.SslOptions.Builder sslOptionsBuilder = io.lettuce.core.SslOptions.builder();
    			sslOptionsBuilder.keyManager(sslBundle.getManagers().getKeyManagerFactory());
    			sslOptionsBuilder.trustManager(sslBundle.getManagers().getTrustManagerFactory());
    			SslOptions sslOptions = sslBundle.getOptions();
    			if (sslOptions.getCiphers() != null) {
    				sslOptionsBuilder.cipherSuites(sslOptions.getCiphers());
    			}
    			if (sslOptions.getEnabledProtocols() != null) {
    				sslOptionsBuilder.protocols(sslOptions.getEnabledProtocols());
    			}
    			builder.sslOptions(sslOptionsBuilder.build());
    		}
    		builder.timeoutOptions(TimeoutOptions.enabled());
    		clientConfigurationBuilderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
    		return builder.build();
    	}
}
