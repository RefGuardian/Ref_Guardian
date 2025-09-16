public class test256 {

    @Bean
    @ConditionalOnMissingBean
    public Authenticator couchbaseAuthenticator(CouchbaseConnectionDetails connectionDetails) throws IOException {
        if (connectionDetails.getUsername() != null && connectionDetails.getPassword() != null) {
            return PasswordAuthenticator.create(connectionDetails.getUsername(), connectionDetails.getPassword());
        }
        Pem pem = getPem();
        if (pem.getCertificates() != null) {
            return getCertificateAuthenticator(pem);
        }
        Jks jks = getJks();
        if (jks.getLocation() != null) {
            return getCertificateAuthenticatorFromKeyStore(jks);
        }
        throw new IllegalStateException("Couchbase authentication requires username and password, or certificates");
    }

    private Pem getPem() {
        return this.properties.getAuthentication().getPem();
    }

    private Jks getJks() {
        return this.properties.getAuthentication().getJks();
    }

    private CertificateAuthenticator getCertificateAuthenticator(Pem pem) {
        PemSslStoreDetails details = new PemSslStoreDetails(null, pem.getCertificates(), pem.getPrivateKey());
        PemSslStore store = PemSslStore.load(details);
        return CertificateAuthenticator.fromKey(store.privateKey(), pem.getPrivateKeyPassword(), store.certificates());
    }

    private CertificateAuthenticator getCertificateAuthenticatorFromKeyStore(Jks jks) throws IOException {
        Resource resource = this.resourceLoader.getResource(jks.getLocation());
        String keystorePassword = jks.getPassword();
        try (InputStream inputStream = resource.getInputStream()) {
            KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
            store.load(inputStream, (keystorePassword != null) ? keystorePassword.toCharArray() : null);
            return CertificateAuthenticator.fromKeyStore(store, keystorePassword);
        } catch (GeneralSecurityException ex) {
            throw new IllegalStateException("Error reading Couchbase certificate store", ex);
        }
    }
}
