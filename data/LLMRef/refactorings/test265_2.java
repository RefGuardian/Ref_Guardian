public class test265 {

    @Override
    		public SslBundle getSslBundle() {
    			if (this.sslBundle != null) {
    				return this.sslBundle;
    			}
    			SslBundle sslBundle = super.getSslBundle();
    			if (sslBundle != null) {
    				this.sslBundle = sslBundle;
    				return sslBundle;
    			}
    			if (hasAnnotation(Ssl.class)) {
    				byte[] caCertificate = getContainer().caCertAsBytes().orElse(null);
    				if (caCertificate != null) {
    					KeyStore trustStore = createTrustStore(caCertificate);
    					SslBundle extractedSslBundle = createSslBundleWithTrustStore(trustStore);
    					this.sslBundle = extractedSslBundle;
    					return extractedSslBundle;
    				}
    			}
    			return null;
    		}
}
