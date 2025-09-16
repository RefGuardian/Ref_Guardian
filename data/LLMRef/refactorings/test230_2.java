public class test230 {

    private void validate() {
        validateProperty("spring.kafka.ssl.key-store-key", getKeyStoreKey(), "spring.kafka.ssl.key-store-location", getKeyStoreLocation());
        validateProperty("spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates(), "spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
        validateProperty("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.key-store-key", getKeyStoreKey());
        validateProperty("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.key-store-location", getKeyStoreLocation());
        validateProperty("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates());
        validateProperty("spring.kafka.ssl.bundle", getBundle(), "spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
    }

    private void validateProperty(String prop1, String value1, String prop2, String value2) {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
            entries.put(prop1, value1);
            entries.put(prop2, value2);
        }, this::hasValue);
    }
}
