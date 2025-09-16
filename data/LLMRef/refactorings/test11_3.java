public class test11 {

    private static final String DIRECT_QUALIFIER_NAME = "directQualifier";
    private static final String DIFFERENT_DIRECT_QUALIFIER_NAME = "differentDirectQualifier";
    private static final String CUSTOM_QUALIFIER_NAME = "customQualifier";

    @Test
    void hashCodeAndEqualsShouldWorkOnDifferentClasses() {
        QualifierDefinition directQualifier1 = QualifierDefinition
            .forElement(ReflectionUtils.findField(ConfigA.class, DIRECT_QUALIFIER_NAME));
        QualifierDefinition directQualifier2 = QualifierDefinition
            .forElement(ReflectionUtils.findField(ConfigB.class, DIRECT_QUALIFIER_NAME));
        QualifierDefinition differentDirectQualifier1 = QualifierDefinition
            .forElement(ReflectionUtils.findField(ConfigA.class, DIFFERENT_DIRECT_QUALIFIER_NAME));
        QualifierDefinition differentDirectQualifier2 = QualifierDefinition
            .forElement(ReflectionUtils.findField(ConfigB.class, DIFFERENT_DIRECT_QUALIFIER_NAME));
        QualifierDefinition customQualifier1 = QualifierDefinition
            .forElement(ReflectionUtils.findField(ConfigA.class, CUSTOM_QUALIFIER_NAME));
        QualifierDefinition customQualifier2 = QualifierDefinition
            .forElement(ReflectionUtils.findField(ConfigB.class, CUSTOM_QUALIFIER_NAME));
        assertThat(directQualifier1).hasSameHashCodeAs(directQualifier2);
        assertThat(differentDirectQualifier1).hasSameHashCodeAs(differentDirectQualifier2);
        assertThat(customQualifier1).hasSameHashCodeAs(customQualifier2);
        assertThat(differentDirectQualifier1).isEqualTo(differentDirectQualifier1)
            .isEqualTo(differentDirectQualifier2)
            .isNotEqualTo(directQualifier2);
        assertThat(directQualifier1).isEqualTo(directQualifier1)
            .isEqualTo(directQualifier2)
            .isNotEqualTo(differentDirectQualifier1);
        assertThat(customQualifier1).isEqualTo(customQualifier1)
            .isEqualTo(customQualifier2)
            .isNotEqualTo(differentDirectQualifier1);
    }
}
