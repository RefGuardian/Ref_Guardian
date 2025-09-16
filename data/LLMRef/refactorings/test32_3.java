public class test32 {

    private static final String LOCATION = "classpath:org/springframework/boot/autoconfigure/ssl/";

    @Test
    void sslBundlesCreatedWithCertificates() {
        List<String> propertyValues = createPropertyValues();
        this.contextRunner.withPropertyValues(propertyValues.toArray(String[]::new)).run((context) -> {
            assertSslBundles(context);
        });
    }

    private List<String> createPropertyValues() {
        List<String> propertyValues = new ArrayList<>();
        addProperty(propertyValues, "spring.ssl.bundle.pem.first.key.alias", "alias1");
        addProperty(propertyValues, "spring.ssl.bundle.pem.first.key.password", "secret1");
        addKeyStoreProperties(propertyValues, "first", "rsa-cert.pem", "rsa-key.pem");
        addTrustStoreProperties(propertyValues, "first", "rsa-cert.pem", "rsa-key.pem");
        addProperty(propertyValues, "spring.ssl.bundle.pem.second.key.alias", "alias2");
        addProperty(propertyValues, "spring.ssl.bundle.pem.second.key.password", "secret2");
        addKeyStoreProperties(propertyValues, "second", "ed25519-cert.pem", "ed25519-key.pem");
        addTrustStoreProperties(propertyValues, "second", "ed25519-cert.pem", "ed25519-key.pem");
        return propertyValues;
    }

    private void addProperty(List<String> propertyValues, String key, String value) {
        propertyValues.add(key + "=" + value);
    }

    private void addKeyStoreProperties(List<String> propertyValues, String bundleName, String certFile, String keyFile) {
        addProperty(propertyValues, "spring.ssl.bundle.pem." + bundleName + ".keystore.certificate", LOCATION + certFile);
        addProperty(propertyValues, "spring.ssl.bundle.pem." + bundleName + ".keystore.private-key", LOCATION + keyFile);
        addProperty(propertyValues, "spring.ssl.bundle.pem." + bundleName + ".keystore.type", "PKCS12");
    }

    private void addTrustStoreProperties(List<String> propertyValues, String bundleName, String certFile, String keyFile) {
        addProperty(propertyValues, "spring.ssl.bundle.pem." + bundleName + ".truststore.certificate", LOCATION + certFile);
        addProperty(propertyValues, "spring.ssl.bundle.pem." + bundleName + ".truststore.private-key", LOCATION + keyFile);
        addProperty(propertyValues, "spring.ssl.bundle.pem." + bundleName + ".truststore.type", "PKCS12");
    }

    private void assertSslBundles(ConfigurableApplicationContext context) {
        assertThat(context).hasSingleBean(SslBundles.class);
        SslBundles bundles = context.getBean(SslBundles.class);
        assertBundle(bundles.getBundle("first"), "alias1", "secret1");
        assertBundle(bundles.getBundle("second"), "alias2", "secret2");
    }

    private void assertBundle(SslBundle bundle, String alias, String password) {
        assertThat(bundle).isNotNull();
        assertThat(bundle.getStores()).isNotNull();
        assertThat(bundle.getManagers()).isNotNull();
        assertThat(bundle.getKey().getAlias()).isEqualTo(alias);
        assertThat(bundle.getKey().getPassword()).isEqualTo(password);
        assertThat(bundle.getStores().getKeyStore().getType()).isEqualTo("PKCS12");
        assertThat(bundle.getStores().getTrustStore().getType()).isEqualTo("PKCS12");
    }
}
