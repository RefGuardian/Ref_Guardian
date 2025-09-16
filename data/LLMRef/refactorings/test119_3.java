public class test119 {

    @Test
    @WithResource(name = "ksLocP")
    @WithResource(name = "tsLocP")
    void adminProperties() {
        this.contextRunner
            .withPropertyValues("spring.kafka.clientId=cid", "spring.kafka.properties.foo.bar.baz=qux.fiz.buz",
                    "spring.kafka.admin.fail-fast=true", "spring.kafka.admin.properties.fiz.buz=fix.fox",
                    "spring.kafka.admin.security.protocol=SSL", "spring.kafka.admin.ssl.key-password=p4",
                    "spring.kafka.admin.ssl.key-store-location=classpath:ksLocP",
                    "spring.kafka.admin.ssl.key-store-password=p5", "spring.kafka.admin.ssl.key-store-type=PKCS12",
                    "spring.kafka.admin.ssl.trust-store-location=classpath:tsLocP",
                    "spring.kafka.admin.ssl.trust-store-password=p6", "spring.kafka.admin.ssl.trust-store-type=PKCS12",
                    "spring.kafka.admin.ssl.protocol=TLSv1.2", "spring.kafka.admin.close-timeout=35s",
                    "spring.kafka.admin.operation-timeout=60s", "spring.kafka.admin.modify-topic-configs=true",
                    "spring.kafka.admin.auto-create=false")
            .run((context) -> {
                KafkaAdmin admin = context.getBean(KafkaAdmin.class);
                Map<String, Object> configs = admin.getConfigurationProperties();
                // common
                assertThat(configs).containsEntry(AdminClientConfig.CLIENT_ID_CONFIG, "cid");
                // admin
                assertThat(configs).containsEntry(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
                assertThat(configs).containsEntry(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "p4");
                assertThat((String) configs.get(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG))
                    .endsWith(File.separator + "ksLocP");
                assertThat(configs).containsEntry(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "p5");
                assertThat(configs).containsEntry(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "PKCS12");
                assertThat((String) configs.get(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG))
                    .endsWith(File.separator + "tsLocP");
                assertThat(configs).containsEntry(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "p6");
                assertThat(configs).containsEntry(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, "PKCS12");
                assertThat(configs).containsEntry(SslConfigs.SSL_PROTOCOL_CONFIG, "TLSv1.2");
                assertThat(context.getBeansOfType(KafkaJaasLoginModuleInitializer.class)).isEmpty();
                assertThat(configs).containsEntry("foo.bar.baz", "qux.fiz.buz");
                assertThat(configs).containsEntry("fiz.buz", "fix.fox");
                assertThat(admin).hasFieldOrPropertyWithValue("closeTimeout", Duration.ofSeconds(35));
                assertThat(admin).hasFieldOrPropertyWithValue("operationTimeout", 60);
                assertThat(admin).hasFieldOrPropertyWithValue("fatalIfBrokerNotAvailable", true);
                assertThat(admin).hasFieldOrPropertyWithValue("modifyTopicConfigs", true);
                assertThat(admin).hasFieldOrPropertyWithValue("autoCreate", false);
            });
    }

}
