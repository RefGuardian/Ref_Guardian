public class test4 {

    @Override
    	protected List<String> getArguments(File archive, File serverPortFile) {
    		try {
    			explodeArchive(archive, this.exploded);
    			deleteLauncherClasses();
    			File builtClasses = populateBuiltClasses(archive);
    			File dependencies = populateDependencies(archive);
    			File resourcesProject = explodedResourcesProject(dependencies);
    			if (archive.getName().endsWith(".war")) {
    				populateSrcMainWebapp();
    			}
    			List<String> classpath = getClasspath(builtClasses, dependencies, resourcesProject);
    			return Arrays.asList("-cp", StringUtils.collectionToDelimitedString(classpath, File.pathSeparator),
    					"com.example.ResourceHandlingApplication", serverPortFile.getAbsolutePath());
    		}
    		catch (IOException ex) {
    			throw new RuntimeException(ex);
    		}
    	}

    private List<String> getClasspath(File builtClasses, File dependencies, File resourcesProject) {
          List<String> classpath = new ArrayList<>();
          classpath.add(builtClasses.getAbsolutePath());
          for (File dependency : dependencies.listFiles()) {
               classpath.add(dependency.getAbsolutePath());
          }
          classpath.add(resourcesProject.getAbsolutePath());
          return classpath;
    }
}
