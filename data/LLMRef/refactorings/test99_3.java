public class test99 {

    private static final String[] PROPERTY_VALUES = { "spring.integration.channel.auto-create=false",
            "spring.integration.channel.max-unicast-subscribers=2",
            "spring.integration.channel.max-broadcast-subscribers=3",
            "spring.integration.error.require-subscribers=false", "spring.integration.error.ignore-failures=false",
            "spring.integration.endpoint.defaultTimeout=60s",
            "spring.integration.endpoint.throw-exception-on-late-reply=true",
            "spring.integration.endpoint.read-only-headers=ignoredHeader",
            "spring.integration.endpoint.no-auto-startup=notStartedEndpoint,_org.springframework.integration.errorLogger" };

    @Test
    void integrationGlobalPropertiesAutoConfigured() {
        assertThat(PROPERTY_VALUES).hasSameSizeAs(globalIntegrationPropertyNames());
        this.contextRunner.withPropertyValues(PROPERTY_VALUES).run((context) -> {
            assertThat(context).hasSingleBean(org.springframework.integration.context.IntegrationProperties.class);
            org.springframework.integration.context.IntegrationProperties integrationProperties = context
                    .getBean(org.springframework.integration.context.IntegrationProperties.class);
            assertThat(integrationProperties.isChannelsAutoCreate()).isFalse();
            assertThat(integrationProperties.getChannelsMaxUnicastSubscribers()).isEqualTo(2);
            assertThat(integrationProperties.getChannelsMaxBroadcastSubscribers()).isEqualTo(3);
            assertThat(integrationProperties.isErrorChannelRequireSubscribers()).isFalse();
            assertThat(integrationProperties.isErrorChannelIgnoreFailures()).isFalse();
            assertThat(integrationProperties.getEndpointsDefaultTimeout()).isEqualTo(60000);
            assertThat(integrationProperties.isMessagingTemplateThrowExceptionOnLateReply()).isTrue();
            assertThat(integrationProperties.getReadOnlyHeaders()).containsOnly("ignoredHeader");
            assertThat(integrationProperties.getNoAutoStartupEndpoints()).containsOnly("notStartedEndpoint",
                    "_org.springframework.integration.errorLogger");
        });
    }
}
