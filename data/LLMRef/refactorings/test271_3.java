public class test271 {

    private void withPathsForPackage(String packageName, ThrowingConsumer<Path> consumer) {
        try {
            List<URL> sources = getSourcesForPackage(packageName);
            for (URL source : sources) {
                URI sourceUri = getSourceUri(source);
                try {
                    consumer.accept(Paths.get(sourceUri));
                }
                catch (FileSystemNotFoundException ex) {
                    try (FileSystem fileSystem = createFileSystem(sourceUri)) {
                        consumer.accept(Paths.get(sourceUri));
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

    private URI getSourceUri(URL source) throws URISyntaxException {
        return source.toURI();
    }

    private FileSystem createFileSystem(URI sourceUri) throws IOException {
        return FileSystems.newFileSystem(sourceUri, Collections.emptyMap());
    }
}
