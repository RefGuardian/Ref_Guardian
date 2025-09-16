public class test38 {

    private static final String KEYSTORE_JKS = "keystore.jks";

    /**
     * Updates many times K8s ConfigMap/Secret with atomic move. <pre>
     * .
     * +─ ..a72e81ff-f0e1-41d8-a19b-068d3d1d4e2f
     * │  +─ keystore.jks
     * +─ ..data -&gt; ..a72e81ff-f0e1-41d8-a19b-068d3d1d4e2f
     * +─ keystore.jks -&gt; ..data/keystore.jks
     * </pre>
     *
     * After a first a ConfigMap/Secret update, this will look like: <pre>
     * .
     * +─ ..bba2a61f-ce04-4c35-93aa-e455110d4487
     * │  +─ keystore.jks
     * +─ ..data -&gt; ..bba2a61f-ce04-4c35-93aa-e455110d4487
     * +─ keystore.jks -&gt; ..data/keystore.jks
     * </pre> After a second a ConfigMap/Secret update, this will look like: <pre>
     * .
     * +─ ..134887f0-df8f-4433-b70c-7784d2a33bd1
     * │  +─ keystore.jks
     * +─ ..data -&gt; ..134887f0-df8f-4433-b70c-7784d2a33bd1
     * +─ keystore.jks -&gt; ..data/keystore.jks
     *</pre>
     * <p>
     * When Kubernetes updates either the ConfigMap or Secret, it performs the following
     * steps:
     * <ul>
     * <li>Creates a new unique directory.</li>
     * <li>Writes the ConfigMap/Secret content to the newly created directory.</li>
     * <li>Creates a symlink {@code ..data_tmp} pointing to the newly created
     * directory.</li>
     * <li>Performs an atomic rename of {@code ..data_tmp} to {@code ..data}.</li>
     * <li>Deletes the old ConfigMap/Secret directory.</li>
     * </ul>
     *
     * @param tempDir temp directory
     * @throws Exception if a failure occurs
     */
    @Test
    void shouldTriggerOnConfigMapAtomicMoveUpdates(@TempDir Path tempDir) throws Exception {
        Path configMap1 = createConfigMap(tempDir, KEYSTORE_JKS);
        Path data = Files.createSymbolicLink(tempDir.resolve("..data"), configMap1);
        Files.createSymbolicLink(tempDir.resolve(KEYSTORE_JKS), data.resolve(KEYSTORE_JKS));
        WaitingCallback callback = new WaitingCallback();
        this.fileWatcher.watch(Set.of(tempDir.resolve(KEYSTORE_JKS)), callback);
        // First update
        Path configMap2 = createConfigMap(tempDir, KEYSTORE_JKS);
        Path dataTmp = Files.createSymbolicLink(tempDir.resolve("..data_tmp"), configMap2);
        move(dataTmp, data);
        FileSystemUtils.deleteRecursively(configMap1);
        callback.expectChanges();
        callback.reset();
        // Second update
        Path configMap3 = createConfigMap(tempDir, KEYSTORE_JKS);
        dataTmp = Files.createSymbolicLink(tempDir.resolve("..data_tmp"), configMap3);
        move(dataTmp, data);
        FileSystemUtils.deleteRecursively(configMap2);
        callback.expectChanges();
    }
}
