public class test144 {

    @Test
    	void customizeClientBuilderWhenHasFailover() {
    		BackupCluster backupCluster1 = new BackupCluster();
    		backupCluster1.setServiceUrl("backup-cluster-1");
    		Map<String, String> params = Map.of("param", "name");
    		backupCluster1.getAuthentication()
    			.setPluginClassName("org.springframework.boot.autoconfigure.pulsar.MockAuthentication");
    		backupCluster1.getAuthentication().setParam(params);
    		BackupCluster backupCluster2 = new BackupCluster();
    		backupCluster2.setServiceUrl("backup-cluster-2");
    		PulsarProperties properties = new PulsarProperties();
    		properties.getClient().setServiceUrl("https://used.example.com");
    		properties.getClient().getFailover().setPolicy(FailoverPolicy.ORDER);
    		properties.getClient().getFailover().setCheckInterval(Duration.ofSeconds(5));
    		properties.getClient().getFailover().setDelay(Duration.ofSeconds(30));
    		properties.getClient().getFailover().setSwitchBackDelay(Duration.ofSeconds(30));
    		properties.getClient().getFailover().setBackupClusters(List.of(backupCluster1, backupCluster2));
    		PulsarConnectionDetails connectionDetails = mock(PulsarConnectionDetails.class);
    		given(connectionDetails.getBrokerUrl()).willReturn("https://used.example.com");
    		ClientBuilder builder = mock(ClientBuilder.class);
    		new PulsarPropertiesMapper(properties).customizeClientBuilder(builder,
    				new PropertiesPulsarConnectionDetails(properties));
    		then(builder).should().serviceUrlProvider(any(AutoClusterFailover.class));
    	}
}
