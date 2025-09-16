public class test177 {

    @Test
    @SuppressWarnings("removal")
    void defaultValuesAreConsistent() {
        FlywayProperties properties = new FlywayProperties();
        Configuration configuration = new FluentConfiguration();
        assertThat(getRetries(properties, configuration)).isTrue();
        assertThat(properties.getBaselineDescription()).isEqualTo(configuration.getBaselineDescription());
        assertThat(MigrationVersion.fromVersion(properties.getBaselineVersion())).isEqualTo(configuration.getBaselineVersion());
        assertThat(properties.getInstalledBy()).isEqualTo(configuration.getInstalledBy());
        assertThat(properties.getPlaceholders()).isEqualTo(configuration.getPlaceholders());
        assertThat(properties.getPlaceholderPrefix()).isEqualToIgnoringWhitespace(configuration.getPlaceholderPrefix());
        assertThat(properties.getPlaceholderSuffix()).isEqualTo(configuration.getPlaceholderSuffix());
        assertThat(properties.isPlaceholderReplacement()).isEqualTo(configuration.isPlaceholderReplacement());
        assertThat(properties.getSqlMigrationPrefix()).isEqualTo(configuration.getSqlMigrationPrefix());
        assertThat(properties.getSqlMigrationSuffixes()).containsExactly(configuration.getSqlMigrationSuffixes());
        assertThat(properties.getSqlMigrationSeparator()).isEqualTo(configuration.getSqlMigrationSeparator());
        assertThat(properties.getRepeatableSqlMigrationPrefix()).isEqualTo(configuration.getRepeatableSqlMigrationPrefix());
        assertThat(MigrationVersion.fromVersion(properties.getTarget())).isEqualTo(configuration.getTarget());
        assertThat(configuration.getInitSql()).isNull();
        assertThat(properties.getInitSqls()).isEmpty();
        assertThat(properties.isBaselineOnMigrate()).isEqualTo(configuration.isBaselineOnMigrate());
        assertThat(properties.isCleanDisabled()).isEqualTo(configuration.isCleanDisabled());
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

    private boolean getRetries(FlywayProperties properties, Configuration configuration) {
        return properties.getConnectRetries() == configuration.getConnectRetries() &&
                properties.getConnectRetriesInterval().getSeconds() == configuration.getConnectRetriesInterval().getSeconds() &&
                properties.getLockRetryCount() == configuration.getLockRetryCount();
    }
}
