public class test230 {

    private void validate() {
        validateMutuallyExclusiveProperties("spring.kafka.ssl.key-store-key", getKeyStoreKey(), "spring.kafka.ssl.key-store-location", getKeyStoreLocation());
        validateMutuallyExclusiveProperties("spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates(), "spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
        validateMutuallyExclusiveProperties("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.key-store-key", getKeyStoreKey());
        validateMutuallyExclusiveProperties("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.key-store-location", getKeyStoreLocation());
        validateMutuallyExclusiveProperties("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates());
        validateMutuallyExclusiveProperties("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
    }

    private void validateMutuallyExclusiveProperties(String key1, Object value1, String key2, Object value2) {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn(entries -> {
            entries.put(key1, value1);
            entries.put(key2, value2);
        }, this::hasValue);
    }
}
