public class test3 {

    @Override
    	protected List<String> getArguments(File archive, File serverPortFile) {
    		try {
    			String archiveName = archive.getName();
    			explodeArchive(archive);
    			deleteLauncherClasses();
    			File targetClasses = populateTargetClasses(archive);
    			File dependencies = populateDependencies(archive);
    			if (archiveName.endsWith(".war")) {
    				populateSrcMainWebapp();
    			}
    			List<String> classpath = new ArrayList<>();
    			classpath.add(targetClasses.getAbsolutePath());
    			for (File dependency : dependencies.listFiles()) {
    				classpath.add(dependency.getAbsolutePath());
    			}
    			return Arrays.asList("-cp", StringUtils.collectionToDelimitedString(classpath, File.pathSeparator),
    					"com.example.ResourceHandlingApplication", serverPortFile.getAbsolutePath());
    		}
    		catch (IOException ex) {
    			throw new RuntimeException(ex);
    		}
    	}
}
