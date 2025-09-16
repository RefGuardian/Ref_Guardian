public class test267 {

    @Override
    	protected List<PropertyGenerator> getPropertyGenerators() {
    		List<PropertyGenerator> generators = new ArrayList<>();
    		String annotationPackage = "org.springframework.boot.autoconfigureprocessor";
    		generators.add(PropertyGenerator.of(annotationPackage, "ConditionalOnClass")
    			.withAnnotation("TestConditionalOnClass", new OnClassConditionValueExtractor()));
    		generators.add(PropertyGenerator.of(annotationPackage, "ConditionalOnBean")
    			.withAnnotation("TestConditionalOnBean", new OnBeanConditionValueExtractor()));
    		generators.add(PropertyGenerator.of(annotationPackage, "ConditionalOnSingleCandidate")
    			.withAnnotation("TestConditionalOnSingleCandidate", new OnBeanConditionValueExtractor()));
    		generators.add(PropertyGenerator.of(annotationPackage, "ConditionalOnWebApplication")
    			.withAnnotation("TestConditionalOnWebApplication", ValueExtractor.allFrom("type")));
    		generators.add(PropertyGenerator.of(annotationPackage, "AutoConfigureBefore", true)
    			.withAnnotation("TestAutoConfigureBefore", ValueExtractor.allFrom("value", "name"))
    			.withAnnotation("TestAutoConfiguration", ValueExtractor.allFrom("before", "beforeName")));
    		generators.add(PropertyGenerator.of(annotationPackage, "AutoConfigureAfter", true)
    			.withAnnotation("TestAutoConfigureAfter", ValueExtractor.allFrom("value", "name"))
    			.withAnnotation("TestAutoConfiguration", ValueExtractor.allFrom("after", "afterName")));
    		generators.add(PropertyGenerator.of(annotationPackage, "AutoConfigureOrder")
    			.withAnnotation("TestAutoConfigureOrder", ValueExtractor.allFrom("value")));
    		return generators;
    	}
}
