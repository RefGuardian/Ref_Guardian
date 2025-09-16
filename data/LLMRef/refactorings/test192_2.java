public class test192 {

    private void applyProperties(MailProperties properties, JavaMailSenderImpl sender, SslBundles sslBundles) {
        setHost(sender, properties.getHost());
        setPort(sender, properties.getPort());
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        setDefaultEncoding(sender, properties.getDefaultEncoding());
        Properties javaMailProperties = asProperties(properties.getProperties());
        String protocol = getProtocol(properties);
        Ssl ssl = properties.getSsl();
        if (ssl.isEnabled()) {
            setSslEnable(javaMailProperties, protocol);
        }
        if (ssl.getBundle() != null) {
            setSslSocketFactory(javaMailProperties, protocol, ssl.getBundle(), sslBundles);
        }
        if (!javaMailProperties.isEmpty()) {
            sender.setJavaMailProperties(javaMailProperties);
        }
    }

    private void setHost(JavaMailSenderImpl sender, String host) {
        sender.setHost(host);
    }

    private void setPort(JavaMailSenderImpl sender, Integer port) {
        if (port != null) {
            sender.setPort(port);
        }
    }

    private void setDefaultEncoding(JavaMailSenderImpl sender, Charset defaultEncoding) {
        if (defaultEncoding != null) {
            sender.setDefaultEncoding(defaultEncoding.name());
        }
    }

    private String getProtocol(MailProperties properties) {
        String protocol = properties.getProtocol();
        return (!StringUtils.hasLength(protocol)) ? "smtp" : protocol;
    }

    private void setSslEnable(Properties javaMailProperties, String protocol) {
        javaMailProperties.setProperty("mail." + protocol + ".ssl.enable", "true");
    }

    private void setSslSocketFactory(Properties javaMailProperties, String protocol, String bundle, SslBundles sslBundles) {
        SslBundle sslBundle = sslBundles.getBundle(bundle);
        javaMailProperties.put("mail." + protocol + ".ssl.socketFactory", sslBundle.createSslContext().getSocketFactory());
    }
}
