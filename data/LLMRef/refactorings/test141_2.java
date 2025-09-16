public class test141 {

    private static final String SPRING_PULSAR_CLIENT_SERVICE_URL = "spring.pulsar.client.service-url";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_DELAY = "spring.pulsar.client.failover.delay";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_SWITCH_BACK_DELAY = "spring.pulsar.client.failover.switch-back-delay";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_CHECK_INTERVAL = "spring.pulsar.client.failover.check-interval";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_0_SERVICE_URL = 
        "spring.pulsar.client.failover.backup-clusters[0].service-url";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_0_PLUGIN_CLASS_NAME = 
        "spring.pulsar.client.failover.backup-clusters[0].authentication.plugin-class-name";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_0_PLUGIN_PARAM_TOKEN = 
        "spring.pulsar.client.failover.backup-clusters[0].authentication.param.token";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_1_SERVICE_URL = 
        "spring.pulsar.client.failover.backup-clusters[1].service-url";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_1_PLUGIN_CLASS_NAME = 
        "spring.pulsar.client.failover.backup-clusters[1].authentication.plugin-class-name";
    private static final String SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_1_PLUGIN_PARAM_TOKEN = 
        "spring.pulsar.client.failover.backup-clusters[1].authentication.param.token";

    @Test
    void bindFailover() {
        Map<String, String> map = new HashMap<>();
        map.put(SPRING_PULSAR_CLIENT_SERVICE_URL, "my-service-url");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_DELAY, "30s");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_SWITCH_BACK_DELAY, "15s");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_CHECK_INTERVAL, "1s");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_0_SERVICE_URL, "backup-service-url-1");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_0_PLUGIN_CLASS_NAME, "com.example.MyAuth1");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_0_PLUGIN_PARAM_TOKEN, "1234");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_1_SERVICE_URL, "backup-service-url-2");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_1_PLUGIN_CLASS_NAME, "com.example.MyAuth2");
        map.put(SPRING_PULSAR_CLIENT_FAILOVER_BACKUP_CLUSTERS_1_PLUGIN_PARAM_TOKEN, "5678");
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
