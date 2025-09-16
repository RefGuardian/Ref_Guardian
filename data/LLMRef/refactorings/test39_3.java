public class test39 {

    @Test
    	void shouldFailIfPemKeystoreCertificateIsEmbedded() {
    		PemSslBundleProperties pem = createPemSslBundleProperties();
    		pem.getKeystore().setCertificate(getCertificate());
    		this.properties.getBundle().getPem().put("bundle1", pem);
    		assertThatIllegalStateException().isThrownBy(() -> this.registrar.registerBundles(this.registry))
    			.withMessageContaining("Unable to register SSL bundle 'bundle1'")
    			.havingCause()
    			.withMessage("Unable to watch for reload on update");
    	}
    
    private PemSslBundleProperties createPemSslBundleProperties() {
    	PemSslBundleProperties pem = new PemSslBundleProperties();
    	pem.setReloadOnUpdate(true);
    	return pem;
    }
    
    private String getCertificate() {
    	return """
    				-----BEGIN CERTIFICATE-----
    				MIICCzCCAb2gAwIBAgIUZbDi7G5czH+Yi0k2EMWxdf00XagwBQYDK2VwMHsxCzAJ
    				BgNVBAYTAlhYMRIwEAYDVQQIDAlTdGF0ZU5hbWUxETAPBgNVBAcMCENpdHlOYW1l
    				M
