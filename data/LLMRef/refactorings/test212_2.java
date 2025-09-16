public class test212 {

    /**
	 * Apply the given properties to a {@link AbstractTemplateViewResolver}. Use Object in
	 * signature to avoid runtime dependency on MVC, which means that the template engine
	 * can be used in a non-web application.
	 * @param viewResolver the resolver to apply the properties to.
	 */
	public void applyToMvcViewResolver(Object viewResolver) {
		Assert.isInstanceOf(AbstractTemplateViewResolver.class, viewResolver,
				() -> "ViewResolver is not an instance of AbstractTemplateViewResolver :" + viewResolver);
		AbstractTemplateViewResolver resolver = (AbstractTemplateViewResolver) viewResolver;
		resolver.setPrefix(getPrefix());
		resolver.setSuffix(getSuffix());
		resolver.setCache(isCache());
		if (getContentType() != null) {
			resolver.setContentType(getContentType().toString());
		}
		resolver.setViewNames(getViewNames());
		resolver.setExposeRequestAttributes(isExposeRequestAttributes());
		resolver.setAllowRequestOverride(isAllowRequestOverride());
		resolver.setAllowSessionOverride(isAllowSessionOverride());
		resolver.setExposeSessionAttributes(isExposeSessionAttributes());
		resolver.setExposeSpringMacroHelpers(isExposeSpringMacroHelpers());
		resolver.setRequestContextAttribute(getRequestContextAttribute());
		// The resolver usually acts as a fallback resolver (e.g. like a
		// InternalResourceViewResolver) so it needs to have low precedence
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
	}
}
