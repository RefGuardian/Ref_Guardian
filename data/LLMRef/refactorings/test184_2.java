public class test184 {

    @Override
    public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
        validateDeferredImportSelector(deferredImportSelector);
        AutoConfigurationImportSelector autoConfigurationImportSelector = (AutoConfigurationImportSelector) deferredImportSelector;
        AutoConfigurationReplacements autoConfigurationReplacements = autoConfigurationImportSelector
                .getAutoConfigurationReplacements();
        validateAutoConfigurationReplacements(autoConfigurationReplacements);
        this.autoConfigurationReplacements = autoConfigurationReplacements;
        AutoConfigurationEntry autoConfigurationEntry = autoConfigurationImportSelector
                .getAutoConfigurationEntry(annotationMetadata);
        this.autoConfigurationEntries.add(autoConfigurationEntry);
        for (String importClassName : autoConfigurationEntry.getConfigurations()) {
            this.entries.putIfAbsent(importClassName, annotationMetadata);
        }
    }

    private void validateDeferredImportSelector(DeferredImportSelector deferredImportSelector) {
        Assert.state(deferredImportSelector instanceof AutoConfigurationImportSelector,
                () -> String.format("Only %s implementations are supported, got %s",
                        AutoConfigurationImportSelector.class.getSimpleName(),
                        deferredImportSelector.getClass().getName()));
    }

    private void validateAutoConfigurationReplacements(AutoConfigurationReplacements autoConfigurationReplacements) {
        Assert.state(
                this.autoConfigurationReplacements == null
                        || this.autoConfigurationReplacements.equals(autoConfigurationReplacements),
                "Auto-configuration replacements must be the same for each call to process");
    }
}
