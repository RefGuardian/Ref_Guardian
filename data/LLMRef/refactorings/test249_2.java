public class test249 {

	private void applyProperties(LettuceClientConfigurationBuilder builder, SslBundle sslBundle) {
		extractLocalMethod(builder);
	}

	private void extractLocalMethod(LettuceClientConfigurationBuilder builder) {
		if (getProperties().getTimeout() != null) {
			builder.commandTimeout(getProperties().getTimeout());
		}
		if (getProperties().getLettuce() != null) {
			RedisProperties.Lettuce lettuce = getProperties().getLettuce();
			extractConstant(lettuce, builder);
		}
	}

	private void extractConstant(RedisProperties.Lettuce lettuce, LettuceClientConfigurationBuilder builder) {
		if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
			builder.shutdownTimeout(getProperties().getLettuce().getShutdownTimeout());
		}
		String readFrom = lettuce.getReadFrom();
		extractLocalVariable(readFrom, builder);
	}

	private void extractLocalVariable(String readFrom, LettuceClientConfigurationBuilder builder) {
		if (readFrom != null) {
			builder.readFrom(getReadFrom(readFrom));
		}
		if (StringUtils.hasText(getProperties().getClientName())) {
			builder.clientName(getProperties().getClientName());
		}
	}

}
