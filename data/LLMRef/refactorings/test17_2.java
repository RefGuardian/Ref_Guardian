public class test17 {

    private static final String ROOT_DIRECTORY_NAME = "spr-servlet";

    @Override
    public URL getResource(String path) throws MalformedURLException {
        URL resource = super.getResource(path);
        if (resource == null && "/".equals(path)) {
            try {
                if (this.emptyRootDirectory == null) {
                    synchronized (this) {
                        File tempDirectory = Files.createTempDirectory(ROOT_DIRECTORY_NAME).toFile();
                        tempDirectory.deleteOnExit();
                        this.emptyRootDirectory = tempDirectory;
                    }
                }
                return this.emptyRootDirectory.toURI().toURL();
            } catch (IOException ex) {
                // Ignore
            }
        }
        return resource;
    }
}
