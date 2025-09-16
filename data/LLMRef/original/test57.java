public class test57 {

    @Test
    	void jwtAuthenticationConverterByJwtConfigIsConditionalOnMissingBean() {
    		String propertiesPrincipalClaim = "principal_from_properties";
    		String propertiesPrincipalValue = "from_props";
    		String userConfigPrincipalValue = "from_user_config";
    		this.contextRunner
    			.withPropertyValues("spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://jwk-set-uri.com",
    					"spring.security.oauth2.resourceserver.jwt.principal-claim-name=" + propertiesPrincipalClaim)
    			.withUserConfiguration(CustomJwtConverterConfig.class)
    			.run((context) -> {
    				JwtAuthenticationConverter converter = context.getBean(JwtAuthenticationConverter.class);
    				Jwt jwt = jwt().claim(propertiesPrincipalClaim, propertiesPrincipalValue)
    					.claim(CustomJwtConverterConfig.PRINCIPAL_CLAIM, userConfigPrincipalValue)
    					.build();
    				AbstractAuthenticationToken token = converter.convert(jwt);
    				assertThat(token).isNotNull()
    					.extracting(AbstractAuthenticationToken::getName)
    					.isEqualTo(userConfigPrincipalValue)
    					.isNotEqualTo(propertiesPrincipalValue);
    				assertThat(context).hasSingleBean(JwtDecoder.class);
    				assertThat(getBearerTokenFilter(context)).isNotNull();
    			});
    	}
}
