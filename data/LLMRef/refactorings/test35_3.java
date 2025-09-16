public class test35 {

    @Test
    void jksPropertiesAreMappedToSslBundle() {
        JksSslBundleProperties properties = createJksSslBundleProperties();
        SslBundle sslBundle = PropertiesSslBundle.get(properties);
        assertSslBundleProperties(sslBundle);
    }

    private JksSslBundleProperties createJksSslBundleProperties() {
        JksSslBundleProperties properties = new JksSslBundleProperties();
        properties.getKey().setAlias("alias");
        properties.getKey().setPassword("secret");
        properties.getOptions().setCiphers(Set.of("cipher1", "cipher2", "cipher3"));
        properties.getOptions().setEnabledProtocols(Set.of("protocol1", "protocol2"));
        properties.getKeystore().setPassword("secret");
        properties.getKeystore().setProvider("SUN");
        properties.getKeystore().setType("JKS");
        properties.getKeystore().setLocation("classpath:org/springframework/boot/autoconfigure/ssl/keystore.jks");
        properties.getTruststore().setPassword("secret");
        properties.getTruststore().setProvider("SUN");
        properties.getTruststore().setType("PKCS12");
        properties.getTruststore().setLocation("classpath:org/springframework/boot/autoconfigure/ssl/keystore.pkcs12");
        return properties;
    }

    private void assertSslBundleProperties(SslBundle sslBundle) {
        assertThat(sslBundle.getKey().getAlias()).isEqualTo("alias");
        assertThat(sslBundle.getKey().getPassword()).isEqualTo("secret");
        assertThat(sslBundle.getOptions().getCiphers()).containsExactlyInAnyOrder("cipher1", "cipher2", "cipher3");
        assertThat(sslBundle.getOptions().getEnabledProtocols()).containsExactlyInAnyOrder("protocol1", "protocol2");
        assertThat(sslBundle.getStores()).isNotNull();
        assertThat(sslBundle.getStores()).extracting("keyStoreDetails")
                .extracting("location", "password", "provider", "type")
                .containsExactly("classpath:org/springframework/boot/autoconfigure/ssl/keystore.jks", "secret", "SUN",
                        "JKS");
        KeyStore trustStore = sslBundle.getStores().getTrustStore();
        assertThat(trustStore.getType()).isEqualTo("PKCS12");
        assertThat(trustStore.getProvider().getName()).isEqualTo("SUN");
    }
}
