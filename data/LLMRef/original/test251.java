public class test251 {

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
    			AnnotationAttributes attributes = AnnotationAttributes
    				.fromMap(metadata.getAnnotationAttributes(EntityScan.class.getName()));
    			Set<String> packagesToScan = new LinkedHashSet<>();
    			for (String basePackage : attributes.getStringArray("basePackages")) {
    				String[] tokenized = StringUtils.tokenizeToStringArray(
    						this.environment.resolvePlaceholders(basePackage),
    						ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
    				Collections.addAll(packagesToScan, tokenized);
    			}
    			for (Class<?> basePackageClass : attributes.getClassArray("basePackageClasses")) {
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
