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
            byte[] caCertificate = extractLocalVariable();
            if (caCertificate != null) {
                KeyStore trustStore = createTrustStore(caCertificate);
                sslBundle = createSslBundleWithTrustStore(trustStore);
                this.sslBundle = sslBundle;
                return sslBundle;
            }
        }
        return null;
    }

    private byte[] extractLocalVariable() {
        return getContainer().caCertAsBytes().orElse(null);
    }
}
