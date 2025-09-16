public class test271 {

    private void withPathsForPackage(String packageName, ThrowingConsumer<Path> consumer) {
        try {
            List<URL> sources = getClassResources(packageName);
            for (URL source : sources) {
                URI sourceUri = source.toURI();
                try {
                    consumer.accept(Paths.get(sourceUri));
                } catch (FileSystemNotFoundException ex) {
                    try (FileSystem fileSystem = FileSystems.newFileSystem(sourceUri, Collections.emptyMap())) {
                        consumer.accept(Paths.get(sourceUri));
                    }
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<URL> getClassResources(String packageName) throws IOException {
        return Collections.list(getClass().getClassLoader().getResources(packageName.replace(".", "/")));
    }
}
