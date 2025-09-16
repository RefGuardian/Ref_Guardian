public class test184 {

    private AutoConfigurationReplacements autoConfigurationReplacements;

    @Override
    public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
        validateDeferredImportSelector(deferredImportSelector);
        AutoConfigurationImportSelector autoConfigurationImportSelector = (AutoConfigurationImportSelector) deferredImportSelector;
        autoConfigurationReplacements = autoConfigurationImportSelector.getAutoConfigurationReplacements();
        checkAutoConfigurationReplacements(autoConfigurationReplacements);
        addAutoConfigurationEntry(autoConfigurationImportSelector, annotationMetadata);
    }

    private void validateDeferredImportSelector(DeferredImportSelector deferredImportSelector) {
        Assert.state(deferredImportSelector instanceof AutoConfigurationImportSelector,
                () -> String.format("Only %s implementations are supported, got %s",
                        AutoConfigurationImportSelector.class.getSimpleName(),
                        deferredImportSelector.getClass().getName()));
    }

    private void checkAutoConfigurationReplacements(AutoConfigurationReplacements replacements) {
        Assert.state(this.autoConfigurationReplacements == null
                        || this.autoConfigurationReplacements.equals(replacements),
                "Auto-configuration replacements must be the same for each call to process");
    }

    private void addAutoConfigurationEntry(AutoConfigurationImportSelector selector, AnnotationMetadata metadata) {
        AutoConfigurationEntry entry = selector.getAutoConfigurationEntry(metadata);
        autoConfigurationEntries.add(entry);
        for (String importClassName : entry.getConfigurations()) {
            entries.putIfAbsent(importClassName, metadata);
        }
    }
}
