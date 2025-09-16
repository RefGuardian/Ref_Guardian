public class test270 {

    private static final String RESOURCE_COPY_COMPLETED_MESSAGE = "Package '%s' did not contain resources: %s";

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
                String.format(RESOURCE_COPY_COMPLETED_MESSAGE, packageName, unmatchedNames));
        return this;
    }
}
