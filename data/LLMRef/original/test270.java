public class test270 {

    Resources addPackage(Package root, String[] resourceNames) {
    		String packageName = root.getName();
    		Set<String> unmatchedNames = new HashSet<>(Arrays.asList(resourceNames));
    		withPathsForPackage(packageName, (packagePath) -> {
    			for (String resourceName : resourceNames) {
    				Path resource = packagePath.resolve(resourceName);
    				if (Files.exists(resource) && !Files.isDirectory(resource)) {
    					Path target = this.root.resolve(resourceName);
    					Path targetDirectory = target.getParent();
    					if (!Files.isDirectory(targetDirectory)) {
    						Files.createDirectories(targetDirectory);
    					}
    					Files.copy(resource, target);
    					register(resourceName, target, true);
    					unmatchedNames.remove(resourceName);
    				}
    			}
    		});
    		Assert.isTrue(unmatchedNames.isEmpty(),
    				"Package '" + packageName + "' did not contain resources: " + unmatchedNames);
    		return this;
    	}
}
