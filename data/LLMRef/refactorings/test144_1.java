public class test144 {

    @Test
    void customizeClientBuilderWhenHasFailover() {
        BackupCluster backupCluster1 = createBackupCluster("backup-cluster-1");
        Map<String, String> params = Map.of("param", "name");
        backupCluster1.getAuthentication().setPluginClassName("org.springframework.boot.autoconfigure.pulsar.MockAuthentication");
        backupCluster1.getAuthentication().setParam(params);
        BackupCluster backupCluster2 = createBackupCluster("backup-cluster-2");
        PulsarProperties properties = createPulsarProperties();
        properties.getClient().getFailover().setPolicy(FailoverPolicy.ORDER);
        properties.getClient().getFailover().setCheckInterval(Duration.ofSeconds(5));
        properties.getClient().getFailover().setDelay(Duration.ofSeconds(30));
        properties.getClient().getFailover().setSwitchBackDelay(Duration.ofSeconds(30));
        properties.getClient().getFailover().setBackupClusters(List.of(backupCluster1, backupCluster2));
        PulsarConnectionDetails connectionDetails = mock(PulsarConnectionDetails.class);
        given(connectionDetails.getBrokerUrl()).willReturn("https://used.example.com");
        ClientBuilder builder = mock(ClientBuilder.class);
        new PulsarPropertiesMapper(properties).customizeClientBuilder(builder, new PropertiesPulsarConnectionDetails(properties));
        then(builder).should().serviceUrlProvider(any(AutoClusterFailover.class));
    }

    private BackupCluster createBackupCluster(String serviceUrl) {
        BackupCluster backupCluster = new BackupCluster();
        backupCluster.setServiceUrl(serviceUrl);
        return backupCluster;
    }

    private PulsarProperties createPulsarProperties() {
        PulsarProperties properties = new PulsarProperties();
        properties.getClient().setServiceUrl("https://used.example.com");
        return properties;
    }
}
