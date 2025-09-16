public class test57 {

    private static final String PROPERTIES_PRINCIPAL_CLAIM = "principal_from_properties";
    private static final String PROPERTIES_PRINCIPAL_VALUE = "from_props";
    private static final String USER_CONFIG_PRINCIPAL_VALUE = "from_user_config";

    @Test
    void jwtAuthenticationConverterByJwtConfigIsConditionalOnMissingBean() {
        this.contextRunner
            .withPropertyValues("spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://jwk-set-uri.com",
                    "spring.security.oauth2.resourceserver.jwt.principal-claim-name=" + PROPERTIES_PRINCIPAL_CLAIM)
            .withUserConfiguration(CustomJwtConverterConfig.class)
            .run((context) -> {
                JwtAuthenticationConverter converter = context.getBean(JwtAuthenticationConverter.class);
                Jwt jwt = jwt().claim(PROPERTIES_PRINCIPAL_CLAIM, PROPERTIES_PRINCIPAL_VALUE)
                    .claim(CustomJwtConverterConfig.PRINCIPAL_CLAIM, USER_CONFIG_PRINCIPAL_VALUE)
                    .build();
                AbstractAuthenticationToken token = converter.convert(jwt);
                assertThat(token).isNotNull()
                    .extracting(AbstractAuthenticationToken::getName)
                    .isEqualTo(USER_CONFIG_PRINCIPAL_VALUE)
                    .isNotEqualTo(PROPERTIES_PRINCIPAL_VALUE);
                assertThat(context).hasSingleBean(JwtDecoder.class);
                assertThat(getBearerTokenFilter(context)).isNotNull();
            });
    }
}
