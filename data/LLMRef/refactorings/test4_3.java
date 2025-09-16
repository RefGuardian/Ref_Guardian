public class test4 {

    @Override
	protected List<String> getArguments(File archive, File serverPortFile) {
		try {
			String explodedPath = this.exploded;
			explodeArchive(archive, explodedPath);
			deleteLauncherClasses();
			File builtClasses = populateBuiltClasses(archive);
			File dependencies = populateDependencies(archive);
			File resourcesProject = explodedResourcesProject(dependencies);
			if (archive.getName().endsWith(".war")) {
				populateSrcMainWebapp();
			}
			List<String> classpath = new ArrayList<>();
			classpath.add(builtClasses.getAbsolutePath());
			for (File dependency : dependencies.listFiles()) {
				classpath.add(dependency.getAbsolutePath());
			}
			classpath.add(resourcesProject.getAbsolutePath());
			String pathSeparator = File.pathSeparator;
			return Arrays.asList("-cp", StringUtils.collectionToDelimitedString(classpath, pathSeparator),
					"com.example.ResourceHandlingApplication", serverPortFile.getAbsolutePath());
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
