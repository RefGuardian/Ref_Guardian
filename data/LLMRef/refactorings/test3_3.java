public class test3 {

	@Override
	protected List<String> getArguments(File archive, File serverPortFile) {
		try {
			explodeArchive(archive);
			deleteLauncherClasses();
			File targetClasses = populateTargetClasses(archive);
			File dependencies = populateDependencies(archive);
			if (archive.getName().endsWith(".war")) {
				populateSrcMainWebapp();
			}
			List<String> classpath = getClasspath(targetClasses, dependencies);
			return Arrays.asList("-cp", StringUtils.collectionToDelimitedString(classpath, File.pathSeparator),
					"com.example.ResourceHandlingApplication", serverPortFile.getAbsolutePath());
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private List<String> getClasspath(File targetClasses, File dependencies) {
		List<String> classpath = new ArrayList<>();
		classpath.add(targetClasses.getAbsolutePath());
		for (File dependency : dependencies.listFiles()) {
			classpath.add(dependency.getAbsolutePath());
		}
		return classpath;
	}
}
