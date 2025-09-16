public class test232 {

    private void mapRequestOptions(CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        Request requestProperties = this.properties.getRequest();
        mapRequestTimeout(requestProperties, options);
        mapRequestConsistency(requestProperties, options);
        mapRequestSerialConsistency(requestProperties, options);
        mapRequestPageSize(requestProperties, options);
        Throttler throttlerProperties = requestProperties.getThrottler();
        mapThrottlerType(throttlerProperties, options);
        mapThrottlerMaxQueueSize(throttlerProperties, options);
        mapThrottlerMaxConcurrentRequests(throttlerProperties, options);
        mapThrottlerMaxRequestsPerSecond(throttlerProperties, options);
        mapThrottlerDrainInterval(throttlerProperties, options);
    }

    private void mapRequestTimeout(Request requestProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(requestProperties::getTimeout)
                .asInt(Duration::toMillis)
                .to(((timeout) -> options.add(DefaultDriverOption.REQUEST_TIMEOUT, timeout)));
    }

    private void mapRequestConsistency(Request requestProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(requestProperties::getConsistency)
                .to(((consistency) -> options.add(DefaultDriverOption.REQUEST_CONSISTENCY, consistency)));
    }

    private void mapRequestSerialConsistency(Request requestProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(requestProperties::getSerialConsistency)
                .to((serialConsistency) -> options.add(DefaultDriverOption.REQUEST_SERIAL_CONSISTENCY, serialConsistency));
    }

    private void mapRequestPageSize(Request requestProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(requestProperties::getPageSize)
                .to((pageSize) -> options.add(DefaultDriverOption.REQUEST_PAGE_SIZE, pageSize));
    }

    private void mapThrottlerType(Throttler throttlerProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(throttlerProperties::getType)
                .as(ThrottlerType::type)
                .to((type) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_CLASS, type));
    }

    private void mapThrottlerMaxQueueSize(Throttler throttlerProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(throttlerProperties::getMaxQueueSize)
                .to((maxQueueSize) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_QUEUE_SIZE, maxQueueSize));
    }

    private void mapThrottlerMaxConcurrentRequests(Throttler throttlerProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(throttlerProperties::getMaxConcurrentRequests)
                .to((maxConcurrentRequests) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_CONCURRENT_REQUESTS,
                        maxConcurrentRequests));
    }

    private void mapThrottlerMaxRequestsPerSecond(Throttler throttlerProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(throttlerProperties::getMaxRequestsPerSecond)
                .to((maxRequestsPerSecond) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_REQUESTS_PER_SECOND,
                        maxRequestsPerSecond));
    }

    private void mapThrottlerDrainInterval(Throttler throttlerProperties, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(throttlerProperties::getDrainInterval)
                .asInt(Duration::toMillis)
                .to((drainInterval) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_DRAIN_INTERVAL, drainInterval));
    }
}
