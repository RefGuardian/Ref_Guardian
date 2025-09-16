public class test17 {

    private static final String SPR_SERVLET = "spr-servlet";

    @Override
    public URL getResource(String path) throws MalformedURLException {
        URL resource = super.getResource(path);
        if (resource == null && "/".equals(path)) {
            // Liquibase assumes that "/" always exists, if we don't have a directory
            // use a temporary location.
            try {
                if (this.emptyRootDirectory == null) {
                    synchronized (this) {
                        File tempDirectory = Files.createTempDirectory(SPR_SERVLET).toFile();
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
