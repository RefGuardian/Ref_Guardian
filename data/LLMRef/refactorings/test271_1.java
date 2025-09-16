public class test271 {

    private void withPathsForPackage(String packageName, ThrowingConsumer<Path> consumer) {
        try {
            List<URL> sources = getSourcesForPackage(packageName);
            for (URL source : sources) {
                Path sourcePath = getSourcePath(source);
                try {
                    consumer.accept(sourcePath);
                }
                catch (FileSystemNotFoundException ex) {
                    try (FileSystem fileSystem = createFileSystem(sourcePath)) {
                        consumer.accept(sourcePath);
                    }
                }
            }
        }
        catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
        catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<URL> getSourcesForPackage(String packageName) throws IOException {
        return Collections.list(getClass().getClassLoader().getResources(packageName.replace(".", "/")));
    }

    private Path getSourcePath(URL source) throws URISyntaxException {
        URI sourceUri = source.toURI();
        return Paths.get(sourceUri);
    }

    private FileSystem createFileSystem(Path sourcePath) throws IOException {
        return FileSystems.newFileSystem(sourcePath.toUri(), Collections.emptyMap());
    }
}
