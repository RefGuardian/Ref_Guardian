public class test37 {

    @Test
	void shouldTriggerOnConfigMapUpdates(@TempDir Path tempDir) throws Exception {
		Path configMap1 = createConfigMap(tempDir, "secret.txt");
		Path configMap2 = createConfigMap(tempDir, "secret.txt");
		Path data = tempDir.resolve("..data");
		Files.createSymbolicLink(data, configMap1);
		Path secretFile = tempDir.resolve("secret.txt");
		Files.createSymbolicLink(secretFile, data.resolve("secret.txt"));
		try {
			WaitingCallback callback = new WaitingCallback();
			this.fileWatcher.watch(Set.of(secretFile), callback);
			Files.delete(data);
			Files.createSymbolicLink(data, configMap2);
			FileSystemUtils.deleteRecursively(configMap1);
			callback.expectChanges();
		}
		finally {
			deleteRecursivelyAndDeleteFiles(data, configMap2, secretFile);
		}
	}

	private void deleteRecursivelyAndDeleteFiles(Path data, Path configMap2, Path secretFile) throws IOException {
		FileSystemUtils.deleteRecursively(configMap2);
		Files.delete(data);
		Files.delete(secretFile);
	}
}
