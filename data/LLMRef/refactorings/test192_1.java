public class test192 {

    private void applyProperties(MailProperties properties, JavaMailSenderImpl sender, SslBundles sslBundles) {
        setHostProperties(properties, sender);
        setPortProperties(properties, sender);
        setUsernameProperties(properties, sender);
        setPasswordProperties(properties, sender);
        setProtocolProperties(properties, sender);
        setDefaultEncodingProperties(properties, sender);
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

    private void setHostProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
    }

    private void setPortProperties(MailProperties properties, JavaMailSenderImpl sender) {
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }
    }

    private void setUsernameProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setUsername(properties.getUsername());
    }

    private void setPasswordProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setPassword(properties.getPassword());
    }

    private void setProtocolProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setProtocol(properties.getProtocol());
    }

    private void setDefaultEncodingProperties(MailProperties properties, JavaMailSenderImpl sender) {
        if (properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }
    }
}
