public class test238 {

    @Bean
    SpringLiquibase liquibase(ObjectProvider<DataSource> dataSource,
            @LiquibaseDataSource ObjectProvider<DataSource> liquibaseDataSource, LiquibaseProperties properties,
            ObjectProvider<SpringLiquibaseCustomizer> customizers, LiquibaseConnectionDetails connectionDetails) {
        SpringLiquibase liquibase = createSpringLiquibase(liquibaseDataSource.getIfAvailable(),
                dataSource.getIfUnique(), connectionDetails);
        customizeLiquibase(liquibase, properties, customizers);
        return liquibase;
    }

    private void customizeLiquibase(SpringLiquibase liquibase, LiquibaseProperties properties, ObjectProvider<SpringLiquibaseCustomizer> customizers) {
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setClearCheckSums(properties.isClearChecksums());
        if (!CollectionUtils.isEmpty(properties.getContexts())) {
            liquibase.setContexts(StringUtils.collectionToCommaDelimitedString(properties.getContexts()));
        }
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setLiquibaseSchema(properties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(properties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(properties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(properties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        if (!CollectionUtils.isEmpty(properties.getLabelFilter())) {
            liquibase.setLabelFilter(StringUtils.collectionToCommaDelimitedString(properties.getLabelFilter()));
        }
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(properties.isTestRollbackOnUpdate());
        liquibase.setTag(properties.getTag());
        if (properties.getShowSummary() != null) {
            liquibase.setShowSummary(UpdateSummaryEnum.valueOf(properties.getShowSummary().name()));
        }
        if (properties.getShowSummaryOutput() != null) {
            liquibase
                    .setShowSummaryOutput(UpdateSummaryOutputEnum.valueOf(properties.getShowSummaryOutput().name()));
        }
        if (properties.getUiService() != null) {
            liquibase.setUiService(UIServiceEnum.valueOf(properties.getUiService().name()));
        }
        if (properties.getAnalyticsEnabled() != null) {
            liquibase.setAnalyticsEnabled(properties.getAnalyticsEnabled());
        }
        if (properties.getLicenseKey() != null) {
            liquibase.setLicenseKey(properties.getLicenseKey());
        }
        customizers.orderedStream().forEach((customizer) -> customizer.customize(liquibase));
    }
}
