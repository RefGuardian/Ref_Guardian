public class test33 {

    private static final String LOCATION = "classpath:org/springframework/boot/autoconfigure/ssl/";

    @Test
    void sslBundlesCreatedWithCustomSslBundle() {
        List<String> propertyValues = new ArrayList<>();
        propertyValues.add("custom.ssl.key.alias=alias1");
        propertyValues.add("custom.ssl.key.password=secret1");
        propertyValues.add("custom.ssl.keystore.certificate=" + LOCATION + "rsa-cert.pem");
        propertyValues.add("custom.ssl.keystore.private-key=" + LOCATION + "rsa-key.pem");
        propertyValues.add("custom.ssl.truststore.certificate=" + LOCATION + "rsa-cert.pem");
        propertyValues.add("custom.ssl.keystore.type=PKCS12");
        propertyValues.add("custom.ssl.truststore.type=PKCS12");
        this.contextRunner.withUserConfiguration(CustomSslBundleConfiguration.class)
                .withPropertyValues(propertyValues.toArray(String[]::new))
                .run((context) -> {
                    assertThat(context).hasSingleBean(SslBundles.class);
                    SslBundles bundles = context.getBean(SslBundles.class);
                    SslBundle bundle = bundles.getBundle("custom");
                    assertThat(bundle).isNotNull();
                    assertThat(bundle.getStores()).isNotNull();
                    assertThat(bundle.getManagers()).isNotNull();
                    assertThat(bundle.getKey().getAlias()).isEqualTo("alias1");
                    assertThat(bundle.getKey().getPassword()).isEqualTo("secret1");
                    assertThat(bundle.getStores().getKeyStore().getType()).isEqualTo("PKCS12");
                    assertThat(bundle.getStores().getTrustStore().getType()).isEqualTo("PKCS12");
                });
    }
}
