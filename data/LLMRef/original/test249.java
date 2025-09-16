public class test249 {

    private void applyProperties(LettuceClientConfigurationBuilder builder, SslBundle sslBundle) {
    		if (sslBundle != null) {
    			builder.useSsl();
    		}
    		if (getProperties().getTimeout() != null) {
    			builder.commandTimeout(getProperties().getTimeout());
    		}
    		if (getProperties().getLettuce() != null) {
    			RedisProperties.Lettuce lettuce = getProperties().getLettuce();
    			if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
    				builder.shutdownTimeout(getProperties().getLettuce().getShutdownTimeout());
    			}
    			String readFrom = lettuce.getReadFrom();
    			if (readFrom != null) {
    				builder.readFrom(getReadFrom(readFrom));
    			}
    		}
    		if (StringUtils.hasText(getProperties().getClientName())) {
    			builder.clientName(getProperties().getClientName());
    		}
    	}
}
