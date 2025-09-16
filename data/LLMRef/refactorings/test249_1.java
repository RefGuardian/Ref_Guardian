public class test249 {

    private void applyProperties(LettuceClientConfigurationBuilder builder, SslBundle sslBundle) {
        applySsl(builder, sslBundle);
        applyCommandTimeout(builder);
        applyLettuceProperties(builder);
        applyClientName(builder);
    }

    private void applySsl(LettuceClientConfigurationBuilder builder, SslBundle sslBundle) {
        if (sslBundle != null) {
            builder.useSsl();
        }
    }

    private void applyCommandTimeout(LettuceClientConfigurationBuilder builder) {
        if (getProperties().getTimeout() != null) {
            builder.commandTimeout(getProperties().getTimeout());
        }
    }

    private void applyLettuceProperties(LettuceClientConfigurationBuilder builder) {
        if (getProperties().getLettuce() != null) {
            RedisProperties.Lettuce lettuce = getProperties().getLettuce();
            applyShutdownTimeout(builder, lettuce);
            applyReadFrom(builder, lettuce);
        }
    }

    private void applyShutdownTimeout(LettuceClientConfigurationBuilder builder, RedisProperties.Lettuce lettuce) {
        if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
            builder.shutdownTimeout(getProperties().getLettuce().getShutdownTimeout());
        }
    }

    private void applyReadFrom(LettuceClientConfigurationBuilder builder, RedisProperties.Lettuce lettuce) {
        String readFrom = lettuce.getReadFrom();
        if (readFrom != null) {
            builder.readFrom(getReadFrom(readFrom));
        }
    }

    private void applyClientName(LettuceClientConfigurationBuilder builder) {
        if (StringUtils.hasText(getProperties().getClientName())) {
            builder.clientName(getProperties().getClientName());
        }
    }
}
