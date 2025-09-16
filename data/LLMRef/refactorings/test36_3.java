public class test36 {

    @Test
    	void shouldFollowRelativePathSymlinks(@TempDir Path tempDir) throws Exception {
    		Path folder = tempDir.resolve("folder");
    		Path live = folder.resolve("live").resolve("certname");
    		Path archive = folder.resolve("archive").resolve("certname");
    		Path link = live.resolve("privkey.pem");
    		Path targetFile = archive.resolve("privkey32.pem");
    		Files.createDirectories(live);
    		Files.createDirectories(archive);
    		Files.createFile(targetFile);
    		Path relativePath = Path.of("../../archive/certname/privkey32.pem");
    		Files.createSymbolicLink(link, relativePath);
    		try {
    			WaitingCallback callback = new WaitingCallback();
    			performFileOperations(link, targetFile, callback);
    		}
    		finally {
    			FileSystemUtils.deleteRecursively(folder);
    		}
    	}

    private void performFileOperations(Path link, Path targetFile, WaitingCallback callback) throws IOException {
    	this.fileWatcher.watch(Set.of(link), callback);
    	Files.writeString(targetFile, "Some content");
    	callback.expectChanges();
    }
}
