public class test11 {

    @Test
    	void hashCodeAndEqualsShouldWorkOnDifferentClasses() {
    		QualifierDefinition directQualifier1 = getQualifierDefinition(ReflectionUtils.findField(ConfigA.class, "directQualifier"));
    		QualifierDefinition directQualifier2 = getQualifierDefinition(ReflectionUtils.findField(ConfigB.class, "directQualifier"));
    		QualifierDefinition differentDirectQualifier1 = getQualifierDefinition(ReflectionUtils.findField(ConfigA.class, "differentDirectQualifier"));
    		QualifierDefinition differentDirectQualifier2 = getQualifierDefinition(ReflectionUtils.findField(ConfigB.class, "differentDirectQualifier"));
    		QualifierDefinition customQualifier1 = getQualifierDefinition(ReflectionUtils.findField(ConfigA.class, "customQualifier"));
    		QualifierDefinition customQualifier2 = getQualifierDefinition(ReflectionUtils.findField(ConfigB.class, "customQualifier"));
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

    private QualifierDefinition getQualifierDefinition(Field field) {
        return QualifierDefinition.forElement(field);
    }
}
