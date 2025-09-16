public class test34 {

    @Test
    void pemPropertiesAreMappedToSslBundle() throws Exception {
        PemSslBundleProperties properties = new PemSslBundleProperties();
        setPemSslBundleProperties(properties);
        SslBundle sslBundle = PropertiesSslBundle.get(properties);
        assertThat(sslBundle.getKey().getAlias()).isEqualTo("alias");
        assertThat(sslBundle.getKey().getPassword()).isEqualTo("secret");
        assertThat(sslBundle.getOptions().getCiphers()).containsExactlyInAnyOrder("cipher1", "cipher2", "cipher3");
        assertThat(sslBundle.getOptions().getEnabledProtocols()).containsExactlyInAnyOrder("protocol1", "protocol2");
        assertThat(sslBundle.getStores()).isNotNull();
        Certificate certificate = sslBundle.getStores().getKeyStore().getCertificate("alias");
        assertThat(certificate).isNotNull();
        assertThat(certificate.getType()).isEqualTo("X.509");
        Key key = sslBundle.getStores().getKeyStore().getKey("alias", "secret".toCharArray());
        assertThat(key).isNotNull();
        assertThat(key.getAlgorithm()).isEqualTo("RSA");
        certificate = sslBundle.getStores().getTrustStore().getCertificate("ssl");
        assertThat(certificate).isNotNull();
        assertThat(certificate.getType()).isEqualTo("X.509");
    }
    
    private void setPemSslBundleProperties(PemSslBundleProperties properties) {
        properties.getKey().setAlias("alias");
        properties.getKey().setPassword("secret");
        properties.getOptions().setCiphers(Set.of("cipher1", "cipher2", "cipher3"));
        properties.getOptions().setEnabledProtocols(Set.of("protocol1", "protocol2"));
        properties.getKeystore().setCertificate("classpath:org/springframework/boot/autoconfigure/ssl/rsa-cert.pem");
        properties.getKeystore().setPrivateKey("classpath:org/springframework/boot/autoconfigure/ssl/rsa-key.pem");
        properties.getKeystore().setPrivateKeyPassword(null);
        properties.getKeystore().setType("PKCS12");
        properties.getTruststore().setCertificate("classpath:org/springframework/boot/autoconfigure/ssl/ed25519-cert.pem");
        properties.getTruststore().setPrivateKey("classpath:org/springframework/boot/autoconfigure/ssl/ed25519-key.pem");
        properties.getTruststore().setPrivateKeyPassword("secret");
        properties.getTruststore().setType("PKCS12");
    }
}
