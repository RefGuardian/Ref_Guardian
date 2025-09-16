public class test230 {

    private void validate() {
        extractMethodLogic1();
        extractMethodLogic2();
        extractMethodLogic3();
        extractMethodLogic4();
        extractMethodLogic5();
        extractMethodLogic6();
    }

    private void extractMethodLogic1() {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
            entries.put("spring.kafka.ssl.key-store-key", getKeyStoreKey());
            entries.put("spring.kafka.ssl.key-store-location", getKeyStoreLocation());
        }, this::hasValue);
    }

    private void extractMethodLogic2() {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
            entries.put("spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates());
            entries.put("spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
        }, this::hasValue);
    }

    private void extractMethodLogic3() {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
            entries.put("spring.kafka.ssl.bundle", getBundle());
            entries.put("spring.kafka.ssl.key-store-key", getKeyStoreKey());
        }, this::hasValue);
    }

    private void extractMethodLogic4() {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
            entries.put("spring.kafka.ssl.bundle", getBundle());
            entries.put("spring.kafka.ssl.key-store-location", getKeyStoreLocation());
        }, this::hasValue);
    }

    private void extractMethodLogic5() {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
            entries.put("spring.kafka.ssl.bundle", getBundle());
            entries.put("spring.kafka.ssl.trust-store-certificates", getTrustStoreCertificates());
        }, this::hasValue);
    }

    private void extractMethodLogic6() {
        MutuallyExclusiveConfigurationPropertiesException.throwIfMultipleMatchingValuesIn((entries) -> {
            entries.put("spring.kafka.ssl.bundle", getBundle());
            entries.put("spring.kafka.ssl.trust-store-location", getTrustStoreLocation());
        }, this::hasValue);
    }
}
