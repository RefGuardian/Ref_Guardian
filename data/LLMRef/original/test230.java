public class test230 {

    private void validate() {
    			MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
    				entries.put("spring.kafka.ssl.key-store-key", getKeyStoreKey());
    				entries.put("spring.kafka.ssl.key-store-location", getKeyStoreLocation());
    			}, this::hasValue);
    			MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
    				entries.put("spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates());
    				entries.put("spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
    			}, this::hasValue);
    			MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
    				entries.put("spring.kafka.ssl.bundle", getBundle());
    				entries.put("spring.kafka.ssl.key-store-key", getKeyStoreKey());
    			}, this::hasValue);
    			MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
    				entries.put("spring.kafka.ssl.bundle", getBundle());
    				entries.put("spring.kafka.ssl.key-store-location", getKeyStoreLocation());
    			}, this::hasValue);
    			MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
    				entries.put("spring.kafka.ssl.bundle", getBundle());
    				entries.put("spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates());
    			}, this::hasValue);
    			MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
    				entries.put("spring.kafka.ssl.bundle", getBundle());
    				entries.put("spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
    			}, this::hasValue);
    		}
}
