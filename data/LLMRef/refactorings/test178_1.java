public class test178 {

    @Test
    void expectedPropertiesAreManaged() {
        Map<String, PropertyDescriptor> properties = indexProperties(PropertyAccessorFactory.forBeanPropertyAccess(new FlywayProperties()));
        Map<String, PropertyDescriptor> configuration = indexProperties(PropertyAccessorFactory.forBeanPropertyAccess(new ClassicConfiguration()));
        // Properties specific settings
        ignoreProperties(properties, "url", "driverClassName", "user", "password", "enabled");
        // Deprecated properties
        ignoreProperties(properties, "oracleKerberosCacheFile", "oracleSqlplus", "oracleSqlplusWarn",
                "oracleWalletLocation", "sqlServerKerberosLoginFile");
        // Properties that are managed by specific extensions
        ignoreProperties(properties, "oracle", "postgresql", "sqlserver");
        // Properties that are only used on the command line
        ignoreProperties(configuration, "jarDirs");
        // https://github.com/flyway/flyway/issues/3732
        ignoreProperties(configuration, "environment");
        // High level object we can't set with properties
        ignoreProperties(configuration, "callbacks", "classLoader", "dataSource", "javaMigrations",
                "javaMigrationClassProvider", "pluginRegister", "resourceProvider", "resolvers");
        // Properties we don't want to expose
        ignoreProperties(configuration, "resolversAsClassNames", "callbacksAsClassNames", "driver", "modernConfig",
                "currentResolvedEnvironment", "reportFilename", "reportEnabled", "workingDirectory",
                "cachedDataSources", "cachedResolvedEnvironments", "currentEnvironmentName", "allEnvironments",
                "environmentProvisionMode", "provisionMode");
        // Handled by the conversion service
        ignoreProperties(configuration, "baselineVersionAsString", "encodingAsString", "locationsAsStrings",
                "targetAsString");
        // Handled as initSql array
        ignoreProperties(configuration, "initSql");
        ignoreProperties(properties, "initSqls");
        // Handled as dryRunOutput
        ignoreProperties(configuration, "dryRunOutputAsFile", "dryRunOutputAsFileName");
        // Handled as createSchemas
        ignoreProperties(configuration, "shouldCreateSchemas");
        // Getters for the DataSource settings rather than actual properties
        ignoreProperties(configuration, "databaseType", "password", "url", "user");
        // Properties not exposed by Flyway
        ignoreProperties(configuration, "failOnMissingTarget");
        // Properties managed by a proprietary extension
        ignoreProperties(configuration, "cherryPick");
        aliasProperty(configuration, "communityDBSupportEnabled", "communityDbSupportEnabled");
        List<String> configurationKeys = new ArrayList<>(configuration.keySet());
        Collections.sort(configurationKeys);
        List<String> propertiesKeys = new ArrayList<>(properties.keySet());
        Collections.sort(propertiesKeys);
        assertThat(configurationKeys).containsExactlyElementsOf(propertiesKeys);
    }

    private void ignoreProperties(Map<String, PropertyDescriptor> properties, String... propertyNames) {
        for (String propertyName : propertyNames) {
            properties.remove(propertyName);
        }
    }
}
