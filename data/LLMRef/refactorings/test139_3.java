public class test139 {

    @Test
    void whenHasUserDefinedFailoverPropertiesAddsToClient() {
        PulsarConnectionDetails connectionDetails = mock(PulsarConnectionDetails.class);
        given(connectionDetails.getBrokerUrl()).willReturn("connectiondetails");
        PulsarConfigurationTests.this.contextRunner.withBean(PulsarConnectionDetails.class, () -> connectionDetails)
            .withPropertyValues("spring.pulsar.client.service-url=properties",
                    "spring.pulsar.client.failover.backup-clusters[0].service-url=backup-cluster-1",
                    "spring.pulsar.client.failover.delay=15s",
                    "spring.pulsar.client.failover.switch-back-delay=30s",
                    "spring.pulsar.client.failover.check-interval=5s",
                    "spring.pulsar.client.failover.backup-clusters[1].service-url=backup-cluster-2",
                    "spring.pulsar.client.failover.backup-clusters[1].authentication.plugin-class-name=org.springframework.boot.autoconfigure.pulsar.MockAuthentication",
                    "spring.pulsar.client.failover.backup-clusters[1].authentication.param.token=1234")
            .run((context) -> {
                DefaultPulsarClientFactory clientFactory = context.getBean(DefaultPulsarClientFactory.class);
                PulsarProperties pulsarProperties = context.getBean(PulsarProperties.class);
                ClientBuilder target = mock(ClientBuilder.class);
                BiConsumer<PulsarClientBuilderCustomizer, ClientBuilder> customizeAction = PulsarClientBuilderCustomizer::customize;
                PulsarClientBuilderCustomizer pulsarClientBuilderCustomizer = (PulsarClientBuilderCustomizer) ReflectionTestUtils
                    .getField(clientFactory, "customizer");
                customizeAction.accept(pulsarClientBuilderCustomizer, target);
                InOrder ordered = inOrder(target);
                ordered.verify(target).serviceUrlProvider(ArgumentMatchers.any(AutoClusterFailover.class));
                assertThat(pulsarProperties.getClient().getFailover().getDelay()).isEqualTo(Duration.ofSeconds(15));
                assertThat(pulsarProperties.getClient().getFailover().getSwitchBackDelay())
                    .isEqualTo(Duration.ofSeconds(30));
                assertThat(pulsarProperties.getClient().getFailover().getCheckInterval())
                    .isEqualTo(Duration.ofSeconds(5));
                assertThat(pulsarProperties.getClient().getFailover().getBackupClusters().size()).isEqualTo(2);
            });
    }
}
