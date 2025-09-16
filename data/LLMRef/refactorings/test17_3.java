public class test17 {

    @Override
    	public URL getResource(String path) throws MalformedURLException {
    		URL resource = super.getResource(path);
    		if (resource == null && "/".equals(path)) {
    			// Liquibase assumes that "/" always exists, if we don't have a directory
    			// use a temporary location.
    			try {
    				if (this.emptyRootDirectory == null) {
    					this.emptyRootDirectory = createTempDirectory();
    				}
    				return this.emptyRootDirectory.toURI().toURL();
    			}
    			catch (IOException ex) {
    				// Ignore
    			}
    		}
    		return resource;
    	}

    	private File createTempDirectory() throws IOException {
    		synchronized(this) {
    			File tempDirectory = Files.createTempDirectory("spr-servlet").toFile();
    			tempDirectory.deleteOnExit();
    			return tempDirectory;
    		}
    	}
}
