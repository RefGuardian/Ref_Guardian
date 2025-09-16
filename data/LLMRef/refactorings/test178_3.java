public class test178 {

    @Test
    void expectedPropertiesAreManaged() {
        Map<String, PropertyDescriptor> properties = indexProperties(
                PropertyAccessorFactory.forBeanPropertyAccess(new FlywayProperties()));
        Map<String, PropertyDescriptor> configuration = indexProperties(
                PropertyAccessorFactory.forBeanPropertyAccess(new ClassicConfiguration()));
        // Properties specific settings
        ignoreProperties(properties, "url", "driverClassName", "user", "password", "enabled");
        // Deprecated properties
        ignoreProperties(properties, "oracleKerberosCacheFile", "oracleSqlplus", "oracleSqlplusWarn",
                "oracleWalletLocation", "sqlServerKerberosLoginFile");
        // Properties that are managed by specific extensions
        ignoreProperties(properties, "oracle", "postgresql", "sqlserver");
        // Properties that are only used on the command line
		List<String> configurationKeys = new ArrayList<>(configuration.keySet());
		Collections.sort(configurationKeys);
		List<String> propertiesKeys = new ArrayList<>(properties.keySet());
		Collections.sort(propertiesKeys);
		assertThat(configurationKeys).containsExactlyElementsOf(propertiesKeys);
    }
}
