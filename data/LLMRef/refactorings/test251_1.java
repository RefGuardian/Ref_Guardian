public class test251 {

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = extractAnnotationAttributes(metadata);
        Set<String> packagesToScan = retrievePackagesToScan(attributes);
        if (packagesToScan.isEmpty()) {
            String packageName = getPackageName(metadata);
            Assert.state(StringUtils.hasLength(packageName), "@EntityScan cannot be used with the default package");
            return Collections.singleton(packageName);
        }
        return packagesToScan;
    }

    private AnnotationAttributes extractAnnotationAttributes(AnnotationMetadata metadata) {
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EntityScan.class.getName()));
    }

    private Set<String> retrievePackagesToScan(AnnotationAttributes attributes) {
        Set<String> packagesToScan = new LinkedHashSet<>();
        processBasePackages(attributes, packagesToScan);
        processBasePackageClasses(attributes, packagesToScan);
        return packagesToScan;
    }

    private String getPackageName(AnnotationMetadata metadata) {
        return ClassUtils.getPackageName(metadata.getClassName());
    }

    private void processBasePackages(AnnotationAttributes attributes, Set<String> packagesToScan) {
        for (String basePackage : attributes.getStringArray("basePackages")) {
            String[] tokenized = StringUtils.tokenizeToStringArray(
                    this.environment.resolvePlaceholders(basePackage),
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            Collections.addAll(packagesToScan, tokenized);
        }
    }

    private void processBasePackageClasses(AnnotationAttributes attributes, Set<String> packagesToScan) {
        for (Class<?> basePackageClass : attributes.getClassArray("basePackageClasses")) {
            packagesToScan.add(this.environment.resolvePlaceholders(ClassUtils.getPackageName(basePackageClass)));
        }
    }
}
