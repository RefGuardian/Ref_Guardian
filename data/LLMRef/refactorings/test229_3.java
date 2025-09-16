public class test229 {

    public Map<String, Object> buildProperties(SslBundles sslBundles) {
        validate();
        String bundleName = getBundle();
        Properties properties = extractProperties();
        if (StringUtils.hasText(bundleName)) {
            return properties;
        }
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this::getKeyPassword).to(properties.in(SslConfigs.SSL_KEY_PASSWORD_CONFIG));
        map.from(this::getKeyStoreCertificateChain)
            .to(properties.in(SslConfigs.SSL_KEYSTORE_CERTIFICATE_CHAIN_CONFIG));
        map.from(this::getKeyStoreKey).to(properties.in(SslConfigs.SSL_KEYSTORE_KEY_CONFIG));
        map.from(this::getKeyStoreLocation)
            .as(this::resourceToPath)
            .to(properties.in(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG));
        map.from(this::getKeyStorePassword).to(properties.in(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG));
        map.from(this::getKeyStoreType).to(properties.in(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG));
        map.from(this::getTrustStoreCertificates).to(properties.in(SslConfigs.SSL_TRUSTSTORE_CERTIFICATES_CONFIG));
        map.from(this::getTrustStoreLocation)
            .as(this::resourceToPath)
            .to(properties.in(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG));
        map.from(this::getTrustStorePassword).to(properties.in(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG));
        map.from(this::getTrustStoreType).to(properties.in(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG));
        map.from(this::getProtocol).to(properties.in(SslConfigs.SSL_PROTOCOL_CONFIG));
        return properties;
    }

    private Properties extractProperties() {
        return new Properties();
    }
}
