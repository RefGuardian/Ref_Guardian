public class test177 {

    @Test
    @SuppressWarnings("removal")
    void defaultValuesAreConsistent() {
        FlywayProperties properties = new FlywayProperties();
        Configuration configuration = new FluentConfiguration();
        assertThat(properties.isFailOnMissingLocations()).isEqualTo(configuration.isFailOnMissingLocations());
        assertThat(properties.getLocations().stream().map(Location::new).toArray(Location[]::new))
                .isEqualTo(configuration.getLocations());
        assertThat(properties.getEncoding()).isEqualTo(configuration.getEncoding());
        assertThat(properties.getConnectRetries()).isEqualTo(configuration.getConnectRetries());
        assertThat(properties.getConnectRetriesInterval()).extracting(Duration::getSeconds)
                .extracting(Long::intValue)
                .isEqualTo(configuration.getConnectRetriesInterval());
        assertThat(properties.getLockRetryCount()).isEqualTo(configuration.getLockRetryCount());
        assertThat(properties.getDefaultSchema()).isEqualTo(configuration.getDefaultSchema());
        assertThat(properties.getSchemas()).isEqualTo(Arrays.asList(configuration.getSchemas()));
        assertThat(properties.isCreateSchemas()).isEqualTo(configuration.isCreateSchemas());
        assertThat(properties.getTable()).isEqualTo(configuration.getTable());
        assertThat(properties.getBaselineDescription()).isEqualTo(configuration.getBaselineDescription());
        assertThat(MigrationVersion.fromVersion(properties.getBaselineVersion()))
                .isEqualTo(configuration.getBaselineVersion());
        assertThat(properties.getInstalledBy()).isEqualTo(configuration.getInstalledBy());
        assertThat(properties.getPlaceholders()).isEqualTo(configuration.getPlaceholders());
        assertThat(properties.getPlaceholderPrefix()).isEqualToIgnoringWhitespace(configuration.getPlaceholderPrefix());
        assertThat(properties.getPlaceholderSuffix()).isEqualTo(configuration.getPlaceholderSuffix());
        assertThat(properties.isPlaceholderReplacement()).isEqualTo(configuration.isPlaceholderReplacement());
        assertThat(properties.getSqlMigrationPrefix()).isEqualTo(configuration.getSqlMigrationPrefix());
        assertThat(properties.getSqlMigrationSuffixes()).containsExactly(configuration.getSqlMigrationSuffixes());
        assertThat(properties.getSqlMigrationSeparator()).isEqualTo(configuration.getSqlMigrationSeparator());
        assertThat(properties.getRepeatableSqlMigrationPrefix())
                .isEqualTo(configuration.getRepeatableSqlMigrationPrefix());
        assertThat(MigrationVersion.fromVersion(properties.getTarget())).isEqualTo(configuration.getTarget());
        assertThat(configuration.getInitSql()).isNull();
        assertThat(properties.getInitSqls()).isEmpty();
        assertThat(properties.isBaselineOnMigrate()).isEqualTo(configuration.isBaselineOnMigrate());
        assertThat(properties.isCleanDisabled()).isEqualTo(configuration.isCleanDisabled());
        assertThat(properties.isCleanOnValidationError()).isEqualTo(configuration.isCleanOnValidationError());
        assertThat(properties.isGroup()).isEqualTo(configuration.isGroup());
        assertThat(properties.isMixed()).isEqualTo(configuration.isMixed());
        assertThat(properties.isOutOfOrder()).isEqualTo(configuration.isOutOfOrder());
        assertThat(properties.isSkipDefaultCallbacks()).isEqualTo(configuration.isSkipDefaultCallbacks());
        assertThat(properties.isSkipDefaultResolvers()).isEqualTo(configuration.isSkipDefaultResolvers());
        assertThat(properties.isValidateMigrationNaming()).isEqualTo(configuration.isValidateMigrationNaming());
        assertThat(properties.isValidateOnMigrate()).isEqualTo(configuration.isValidateOnMigrate());
        assertThat(properties.getDetectEncoding()).isNull();
        assertThat(properties.getPlaceholderSeparator()).isEqualTo(configuration.getPlaceholderSeparator());
        assertThat(properties.getScriptPlaceholderPrefix()).isEqualTo(configuration.getScriptPlaceholderPrefix());
        assertThat(properties.getScriptPlaceholderSuffix()).isEqualTo(configuration.getScriptPlaceholderSuffix());
        assertThat(properties.isExecuteInTransaction()).isEqualTo(configuration.isExecuteInTransaction());
        assertThat(properties.getCommunityDbSupportEnabled()).isNull();
    }
}
