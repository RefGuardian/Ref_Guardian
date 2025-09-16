public class test11 {

    @Test
        void hashCodeAndEqualsShouldWorkOnDifferentClasses() {
            QualifierDefinition directQualifier1 = getQualifierDefinition(ConfigA.class, "directQualifier");
            QualifierDefinition directQualifier2 = getQualifierDefinition(ConfigB.class, "directQualifier");
            QualifierDefinition differentDirectQualifier1 = getQualifierDefinition(ConfigA.class, "differentDirectQualifier");
            QualifierDefinition differentDirectQualifier2 = getQualifierDefinition(ConfigB.class, "differentDirectQualifier");
            QualifierDefinition customQualifier1 = getQualifierDefinition(ConfigA.class, "customQualifier");
            QualifierDefinition customQualifier2 = getQualifierDefinition(ConfigB.class, "customQualifier");
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

    private QualifierDefinition getQualifierDefinition(Class<?> configClass, String qualifier) {
        return QualifierDefinition.forElement(ReflectionUtils.findField(configClass, qualifier));
    }
}
