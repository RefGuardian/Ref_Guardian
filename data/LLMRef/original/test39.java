public class test39 {

    @Test
    	void shouldFailIfPemKeystoreCertificateIsEmbedded() {
    		PemSslBundleProperties pem = new PemSslBundleProperties();
    		pem.setReloadOnUpdate(true);
    		pem.getKeystore().setCertificate("""
    				-----BEGIN CERTIFICATE-----
    				MIICCzCCAb2gAwIBAgIUZbDi7G5czH+Yi0k2EMWxdf00XagwBQYDK2VwMHsxCzAJ
    				BgNVBAYTAlhYMRIwEAYDVQQIDAlTdGF0ZU5hbWUxETAPBgNVBAcMCENpdHlOYW1l
    				MRQwEgYDVQQKDAtDb21wYW55TmFtZTEbMBkGA1UECwwSQ29tcGFueVNlY3Rpb25O
    				YW1lMRIwEAYDVQQDDAlsb2NhbGhvc3QwHhcNMjMwOTExMTIxNDMwWhcNMzMwOTA4
    				MTIxNDMwWjB7MQswCQYDVQQGEwJYWDESMBAGA1UECAwJU3RhdGVOYW1lMREwDwYD
    				VQQHDAhDaXR5TmFtZTEUMBIGA1UECgwLQ29tcGFueU5hbWUxGzAZBgNVBAsMEkNv
    				bXBhbnlTZWN0aW9uTmFtZTESMBAGA1UEAwwJbG9jYWxob3N0MCowBQYDK2VwAyEA
    				Q/DDA4BSgZ+Hx0DUxtIRjVjN+OcxXVURwAWc3Gt9GUyjUzBRMB0GA1UdDgQWBBSv
    				EdpoaBMBoxgO96GFbf03k07DSTAfBgNVHSMEGDAWgBSvEdpoaBMBoxgO96GFbf03
    				k07DSTAPBgNVHRMBAf8EBTADAQH/MAUGAytlcANBAHMXDkGd57d4F4cRk/8UjhxD
    				7OtRBZfdfznSvlhJIMNfH5q0zbC2eO3hWCB3Hrn/vIeswGP8Ov4AJ6eXeX44BQM=
    				-----END CERTIFICATE-----
    				""".strip());
    		this.properties.getBundle().getPem().put("bundle1", pem);
    		assertThatIllegalStateException().isThrownBy(() -> this.registrar.registerBundles(this.registry))
    			.withMessageContaining("Unable to register SSL bundle 'bundle1'")
    			.havingCause()
    			.withMessage("Unable to watch for reload on update");
    	}
}
