public class test35 {

    private static final String ALIAS = "alias";
    private static final String SECRET = "secret";
    private static final String CIPHER1 = "cipher1";
    private static final String CIPHER2 = "cipher2";
    private static final String CIPHER3 = "cipher3";
    private static final String PROTOCOL1 = "protocol1";
    private static final String PROTOCOL2 = "protocol2";
    private static final String SUN = "SUN";
    private static final String JKS = "JKS";
    private static final String PKCS12 = "PKCS12";
    private static final String KEYSTORE_LOCATION = "classpath:org/springframework/boot/autoconfigure/ssl/keystore.jks";
    private static final String TRUSTSTORE_LOCATION = "classpath:org/springframework/boot/autoconfigure/ssl/keystore.pkcs12";

    @Test
    void jksPropertiesAreMappedToSslBundle() {
        JksSslBundleProperties properties = new JksSslBundleProperties();
        properties.getKey().setAlias(ALIAS);
        properties.getKey().setPassword(SECRET);
        properties.getOptions().setCiphers(Set.of(CIPHER1, CIPHER2, CIPHER3));
        properties.getOptions().setEnabledProtocols(Set.of(PROTOCOL1, PROTOCOL2));
        properties.getKeystore().setPassword(SECRET);
        properties.getKeystore().setProvider(SUN);
        properties.getKeystore().setType(JKS);
        properties.getKeystore().setLocation(KEYSTORE_LOCATION);
        properties.getTruststore().setPassword(SECRET);
        properties.getTruststore().setProvider(SUN);
        properties.getTruststore().setType(PKCS12);
        properties.getTruststore().setLocation(TRUSTSTORE_LOCATION);
        SslBundle sslBundle = PropertiesSslBundle.get(properties);
        assertThat(sslBundle.getKey().getAlias()).isEqualTo(ALIAS);
        assertThat(sslBundle.getKey().getPassword()).isEqualTo(SECRET);
        assertThat(sslBundle.getOptions().getCiphers()).containsExactlyInAnyOrder(CIPHER1, CIPHER2, CIPHER3);
        assertThat(sslBundle.getOptions().getEnabledProtocols()).containsExactlyInAnyOrder(PROTOCOL1, PROTOCOL2);
        assertThat(sslBundle.getStores()).isNotNull();
        assertThat(sslBundle.getStores()).extracting("keyStoreDetails")
            .extracting("location", "password", "provider", "type")
            .containsExactly(KEYSTORE_LOCATION, SECRET, SUN, JKS);
        KeyStore trustStore = sslBundle.getStores().getTrustStore();
        assertThat(trustStore.getType()).isEqualTo(PKCS12);
        assertThat(trustStore.getProvider().getName()).isEqualTo(SUN);
    }
}
