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
        watchFileAndVerifyContent(link, targetFile);
    }

    private void watchFileAndVerifyContent(Path link, Path targetFile) throws IOException {
        try {
            WaitingCallback callback = new WaitingCallback();
            this.fileWatcher.watch(Set.of(link), callback);
            Files.writeString(targetFile, "Some content");
            callback.expectChanges();
        } finally {
            FileSystemUtils.deleteRecursively(link.getParent());
        }
    }
}
