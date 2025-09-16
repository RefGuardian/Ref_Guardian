public class test267 {

    @Override
	protected List<PropertyGenerator> getPropertyGenerators() {
		List<PropertyGenerator> generators = createGenerators();
		return generators;
	}

	private List<PropertyGenerator> createGenerators() {
		List<PropertyGenerator> generators = new ArrayList<>();
		String annotationPackage = "org.springframework.boot.autoconfigureprocessor";
		addGenerator(generators, annotationPackage, "ConditionalOnClass", "TestConditionalOnClass", new OnClassConditionValueExtractor());
		addGenerator(generators, annotationPackage, "ConditionalOnBean", "TestConditionalOnBean", new OnBeanConditionValueExtractor());
		addGenerator(generators, annotationPackage, "ConditionalOnSingleCandidate", "TestConditionalOnSingleCandidate", new OnBeanConditionValueExtractor());
		addGenerator(generators, annotationPackage, "ConditionalOnWebApplication", "TestConditionalOnWebApplication", ValueExtractor.allFrom("type"));
		addGenerator(generators, annotationPackage, "AutoConfigureBefore", "TestAutoConfigureBefore", ValueExtractor.allFrom("value", "name"), "TestAutoConfiguration", ValueExtractor.allFrom("before", "beforeName"));
		addGenerator(generators, annotationPackage, "AutoConfigureAfter", "TestAutoConfigureAfter", ValueExtractor.allFrom("value", "name"), "TestAutoConfiguration", ValueExtractor.allFrom("after", "afterName"));
		addGenerator(generators, annotationPackage, "AutoConfigureOrder", "TestAutoConfigureOrder", ValueExtractor.allFrom("value"));
		return generators;
	}

	private void addGenerator(List<PropertyGenerator> generators, String annotationPackage, String annotationType, String annotationName, ValueExtractor valueExtractor) {
		generators.add(PropertyGenerator.of(annotationPackage, annotationType)
			.withAnnotation(annotationName, valueExtractor));
	}

	private void addGenerator(List<PropertyGenerator> generators, String annotationPackage, String annotationType, String annotationName, ValueExtractor valueExtractor1, String annotationName2, ValueExtractor valueExtractor2) {
		generators.add(PropertyGenerator.of(annotationPackage, annotationType, true)
			.withAnnotation(annotationName, valueExtractor1)
			.withAnnotation(annotationName2, valueExtractor2));
	}
}
