public class test251 {

	private static final String ENTITY_SCAN_CLASS_NAME = EntityScan.class.getName();
	private static final String BASE_PACKAGES = "basePackages";
	private static final String BASE_PACKAGE_CLASSES = "basePackageClasses";
	private static final String CONFIG_LOCATION_DELIMITERS = ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS;

	private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ENTITY_SCAN_CLASS_NAME));
		Set<String> packagesToScan = new LinkedHashSet<>();
		for (String basePackage : attributes.getStringArray(BASE_PACKAGES)) {
			String[] tokenized = StringUtils.tokenizeToStringArray(
					this.environment.resolvePlaceholders(basePackage),
					CONFIG_LOCATION_DELIMITERS);
			Collections.addAll(packagesToScan, tokenized);
		}
		for (Class<?> basePackageClass : attributes.getClassArray(BASE_PACKAGE_CLASSES)) {
			packagesToScan.add(this.environment.resolvePlaceholders(ClassUtils.getPackageName(basePackageClass)));
		}
		if (packagesToScan.isEmpty()) {
			String packageName = ClassUtils.getPackageName(metadata.getClassName());
			Assert.state(StringUtils.hasLength(packageName), "@EntityScan cannot be used with the default package");
			return Collections.singleton(packageName);
		}
		return packagesToScan;
	}
}
