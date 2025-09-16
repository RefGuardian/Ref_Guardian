public class test270 {

    Resources addPackage(Package root, String[] resourceNames) {
        String packageName = root.getName();
        Set<String> unmatchedNames = new HashSet<>(Arrays.asList(resourceNames));
        withPathsForPackage(packageName, this::copyResources);
        Assert.isTrue(unmatchedNames.isEmpty(),
                "Package '" + packageName + "' did not contain resources: " + unmatchedNames);
        return this;
    }

    private void copyResources(String packagePath, String[] resourceNames) {
        for (String resourceName : resourceNames) {
            Path resource = Paths.get(packagePath).resolve(resourceName);
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
}
