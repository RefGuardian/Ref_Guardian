public class test206 {

    private static final String INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME = IntegrationContextUtils.INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME;

    @Bean(name = INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME)
    @ConditionalOnMissingBean(name = INTEGRATION_GLOBAL_PROPERTIES_BEAN_NAME)
    public static org.springframework.integration.context.IntegrationProperties integrationGlobalProperties(
            IntegrationProperties properties) {
        org.springframework.integration.context.IntegrationProperties integrationProperties = new org.springframework.integration.context.IntegrationProperties();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties.getChannel().isAutoCreate()).to(integrationProperties::setChannelsAutoCreate);
        map.from(properties.getChannel().getMaxUnicastSubscribers())
                .to(integrationProperties::setChannelsMaxUnicastSubscribers);
        map.from(properties.getChannel().getMaxBroadcastSubscribers())
                .to(integrationProperties::setChannelsMaxBroadcastSubscribers);
        map.from(properties.getError().isRequireSubscribers())
                .to(integrationProperties::setErrorChannelRequireSubscribers);
        map.from(properties.getError().isIgnoreFailures()).to(integrationProperties::setErrorChannelIgnoreFailures);
        map.from(properties.getEndpoint().isThrowExceptionOnLateReply())
                .to(integrationProperties::setMessagingTemplateThrowExceptionOnLateReply);
        map.from(properties.getEndpoint().getDefaultTimeout())
                .as(Duration::toMillis)
                .to(integrationProperties::setEndpointsDefaultTimeout);
        map.from(properties.getEndpoint().getReadOnlyHeaders())
                .as(StringUtils::toStringArray)
                .to(integrationProperties::setReadOnlyHeaders);
        map.from(properties.getEndpoint().getNoAutoStartup())
                .as(StringUtils::toStringArray)
                .to(integrationProperties::setNoAutoStartupEndpoints);
        return integrationProperties;
    }
}
