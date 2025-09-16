public class test192 {

    private void applyProperties(MailProperties properties, JavaMailSenderImpl sender, SslBundles sslBundles) {
        extractMethod(properties, sender);
    }

    private void extractMethod(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        if (properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }
        Properties javaMailProperties = asProperties(properties.getProperties());
        String protocol = properties.getProtocol();
        protocol = (!StringUtils.hasLength(protocol)) ? "smtp" : protocol;
        Ssl ssl = properties.getSsl();
        if (ssl.isEnabled()) {
            javaMailProperties.setProperty("mail." + protocol + ".ssl.enable", "true");
        }
        if (ssl.getBundle() != null) {
            SslBundle sslBundle = sslBundles.getBundle(ssl.getBundle());
            javaMailProperties.put("mail." + protocol + ".ssl.socketFactory",
                    sslBundle.createSslContext().getSocketFactory());
        }
        if (!javaMailProperties.isEmpty()) {
            sender.setJavaMailProperties(javaMailProperties);
        }
    }
}
