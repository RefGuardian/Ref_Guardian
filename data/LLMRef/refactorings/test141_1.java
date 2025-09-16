public class test141 {

    @Test
    void bindFailover() {
        Map<String, String> map = new HashMap<>();
        map.put("spring.pulsar.client.service-url", "my-service-url");
        map.put("spring.pulsar.client.failover.delay", "30s");
        map.put("spring.pulsar.client.failover.switch-back-delay", "15s");
        map.put("spring.pulsar.client.failover.check-interval", "1s");
        map.put("spring.pulsar.client.failover.backup-clusters[0].service-url", "backup-service-url-1");
        map.put("spring.pulsar.client.failover.backup-clusters[0].authentication.plugin-class-name",
                "com.example.MyAuth1");
        map.put("spring.pulsar.client.failover.backup-clusters[0].authentication.param.token", "1234");
        map.put("spring.pulsar.client.failover.backup-clusters[1].service-url", "backup-service-url-2");
        map.put("spring.pulsar.client.failover.backup-clusters[1].authentication.plugin-class-name",
                "com.example.MyAuth2");
        map.put("spring.pulsar.client.failover.backup-clusters[1].authentication.param.token", "5678");
        PulsarProperties.Client properties = bindProperties(map).getClient();
        Failover failoverProperties = properties.getFailover();
        List<BackupCluster> backupClusters = properties.getFailover().getBackupClusters();
        assertThat(properties.getServiceUrl()).isEqualTo("my-service-url");
        assertThat(failoverProperties.getDelay()).isEqualTo(Duration.ofMillis(30000));
        assertThat(failoverProperties.getSwitchBackDelay()).isEqualTo(Duration.ofMillis(15000));
        assertThat(failoverProperties.getCheckInterval()).isEqualTo(Duration.ofMillis(1000));
        assertThat(backupClusters.get(0).getServiceUrl()).isEqualTo("backup-service-url-1");
        assertThat(backupClusters.get(0).getAuthentication().getPluginClassName()).isEqualTo("com.example.MyAuth1");
        assertThat(backupClusters.get(0).getAuthentication().getParam()).containsEntry("token", "1234");
        assertThat(backupClusters.get(1).getServiceUrl()).isEqualTo("backup-service-url-2");
        assertThat(backupClusters.get(1).getAuthentication().getPluginClassName()).isEqualTo("com.example.MyAuth2");
        assertThat(backupClusters.get(1).getAuthentication().getParam()).containsEntry("token", "5678");
    }

}
