public class test32 {

    @Test
    	void sslBundlesCreatedWithCertificates() {
    		List<String> propertyValues = getPropertyValues();
    		String location = "classpath:org/springframework/boot/autoconfigure/ssl/";
    		this.contextRunner.withPropertyValues(propertyValues.toArray(String[]::new)).run((context) -> {
    			assertThat(context).hasSingleBean(SslBundles.class);
    			SslBundles bundles = context.getBean(SslBundles.class);
    			SslBundle first = bundles.getBundle("first");
    			assertThat(first).isNotNull();
    			assertThat(first.getStores()).isNotNull();
    			assertThat(first.getManagers()).isNotNull();
    			assertThat(first.getKey().getAlias()).isEqualTo("alias1");
    			assertThat(first.getKey().getPassword()).isEqualTo("secret1");
    			assertThat(first.getStores().getKeyStore().getType()).isEqualTo("PKCS12");
    			assertThat(first.getStores().getTrustStore().getType()).isEqualTo("PKCS12");
    			SslBundle second = bundles.getBundle("second");
    			assertThat(second).isNotNull();
    			assertThat(second.getStores()).isNotNull();
    			assertThat(second.getManagers()).isNotNull();
    			assertThat(second.getKey().getAlias()).isEqualTo("alias2");
    			assertThat(second.getKey().getPassword()).isEqualTo("secret2");
    			assertThat(second.getStores().getKeyStore().getType()).isEqualTo("PKCS12");
    			assertThat(second.getStores().getTrustStore().getType()).isEqualTo("PKCS12");
    		});
    	}

    	List<String> getPropertyValues() {
    		List<String> propertyValues = new ArrayList<>();
    		String location = "classpath:org/springframework/boot/autoconfigure/ssl/";
    		propertyValues.add("spring.ssl.bundle.pem.first.key.alias=alias1");
    		propertyValues.add("spring.ssl.bundle.pem.first.key.password=secret1");
    		propertyValues.add("spring.ssl.bundle.pem.first.keystore.certificate=" + location + "rsa-cert.pem");
    		propertyValues.add("spring.ssl.bundle.pem.first.keystore.private-key=" + location + "rsa-key.pem");
    		propertyValues.add("spring.ssl.bundle.pem.first.keystore.type=PKCS12");
    		propertyValues.add("spring.ssl.bundle.pem.first.truststore.type=PKCS12");
    		propertyValues.add("spring.ssl.bundle.pem.first.truststore.certificate=" + location + "rsa-cert.pem");
    		propertyValues.add("spring.ssl.bundle.pem.first.truststore.private-key=" + location + "rsa-key.pem");
    		propertyValues.add("spring.ssl.bundle.pem.second.key.alias=alias2");
    		propertyValues.add("spring.ssl.bundle.pem.second.key.password=secret2");
    		propertyValues.add("spring.ssl.bundle.pem.second.keystore.certificate=" + location + "ed25519-cert.pem");
    		propertyValues.add("spring.ssl.bundle.pem.second.keystore.private-key=" + location + "ed25519-key.pem");
    		propertyValues.add("spring.ssl.bundle.pem.second.keystore.type=PKCS12");
    		propertyValues.add("spring.ssl.bundle.pem.second.truststore.certificate=" + location + "ed25519-cert.pem");
    		propertyValues.add("spring.ssl.bundle.pem.second.truststore.private-key=" + location + "ed25519-key.pem");
    		propertyValues.add("spring.ssl.bundle.pem.second.truststore.type=PKCS12");
    		return propertyValues;
    	}
}
