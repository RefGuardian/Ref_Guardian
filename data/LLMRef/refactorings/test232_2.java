public class test232 {

    private void mapRequestOptions(CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        Request requestProperties = this.properties.getRequest();
        processTimeout(map, options, requestProperties);
        processConsistency(map, options, requestProperties);
        processSerialConsistency(map, options, requestProperties);
        processPageSize(map, options, requestProperties);
        Throttler throttlerProperties = requestProperties.getThrottler();
        processThrottlerType(map, options, throttlerProperties);
        processMaxQueueSize(map, options, throttlerProperties);
        processMaxConcurrentRequests(map, options, throttlerProperties);
        processMaxRequestsPerSecond(map, options, throttlerProperties);
        processDrainInterval(map, options, throttlerProperties);
    }

    private void processTimeout(PropertyMapper map, CassandraDriverOptions options, Request requestProperties) {
        map.from(requestProperties::getTimeout)
            .asInt(Duration::toMillis)
            .to(((timeout) -> options.add(DefaultDriverOption.REQUEST_TIMEOUT, timeout)));
    }

    private void processConsistency(PropertyMapper map, CassandraDriverOptions options, Request requestProperties) {
        map.from(requestProperties::getConsistency)
            .to(((consistency) -> options.add(DefaultDriverOption.REQUEST_CONSISTENCY, consistency)));
    }

    private void processSerialConsistency(PropertyMapper map, CassandraDriverOptions options, Request requestProperties) {
        map.from(requestProperties::getSerialConsistency)
            .to((serialConsistency) -> options.add(DefaultDriverOption.REQUEST_SERIAL_CONSISTENCY, serialConsistency));
    }

    private void processPageSize(PropertyMapper map, CassandraDriverOptions options, Request requestProperties) {
        map.from(requestProperties::getPageSize)
            .to((pageSize) -> options.add(DefaultDriverOption.REQUEST_PAGE_SIZE, pageSize));
    }

    private void processThrottlerType(PropertyMapper map, CassandraDriverOptions options, Throttler throttlerProperties) {
        map.from(throttlerProperties::getType)
            .as(ThrottlerType::type)
            .to((type) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_CLASS, type));
    }

    private void processMaxQueueSize(PropertyMapper map, CassandraDriverOptions options, Throttler throttlerProperties) {
        map.from(throttlerProperties::getMaxQueueSize)
            .to((maxQueueSize) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_QUEUE_SIZE, maxQueueSize));
    }

    private void processMaxConcurrentRequests(PropertyMapper map, CassandraDriverOptions options, Throttler throttlerProperties) {
        map.from(throttlerProperties::getMaxConcurrentRequests)
            .to((maxConcurrentRequests) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_CONCURRENT_REQUESTS, maxConcurrentRequests));
    }

    private void processMaxRequestsPerSecond(PropertyMapper map, CassandraDriverOptions options, Throttler throttlerProperties) {
        map.from(throttlerProperties::getMaxRequestsPerSecond)
            .to((maxRequestsPerSecond) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_REQUESTS_PER_SECOND, maxRequestsPerSecond));
    }

    private void processDrainInterval(PropertyMapper map, CassandraDriverOptions options, Throttler throttlerProperties) {
        map.from(throttlerProperties::getDrainInterval)
            .asInt(Duration::toMillis)
            .to((drainInterval) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_DRAIN_INTERVAL, drainInterval));
    }
}
