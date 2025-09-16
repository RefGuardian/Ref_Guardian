public class test232 {

    private void mapRequestOptions(CassandraDriverOptions options) {
    		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
    		Request requestProperties = this.properties.getRequest();
    		map.from(requestProperties::getTimeout)
    			.asInt(Duration::toMillis)
    			.to(((timeout) -> options.add(DefaultDriverOption.REQUEST_TIMEOUT, timeout)));
    		map.from(requestProperties::getConsistency)
    			.to(((consistency) -> options.add(DefaultDriverOption.REQUEST_CONSISTENCY, consistency)));
    		map.from(requestProperties::getSerialConsistency)
    			.to((serialConsistency) -> options.add(DefaultDriverOption.REQUEST_SERIAL_CONSISTENCY, serialConsistency));
    		map.from(requestProperties::getPageSize)
    			.to((pageSize) -> options.add(DefaultDriverOption.REQUEST_PAGE_SIZE, pageSize));
    		Throttler throttlerProperties = requestProperties.getThrottler();
    		map.from(throttlerProperties::getType)
    			.as(ThrottlerType::type)
    			.to((type) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_CLASS, type));
    		map.from(throttlerProperties::getMaxQueueSize)
    			.to((maxQueueSize) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_QUEUE_SIZE, maxQueueSize));
    		map.from(throttlerProperties::getMaxConcurrentRequests)
    			.to((maxConcurrentRequests) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_CONCURRENT_REQUESTS,
    					maxConcurrentRequests));
    		map.from(throttlerProperties::getMaxRequestsPerSecond)
    			.to((maxRequestsPerSecond) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_MAX_REQUESTS_PER_SECOND,
    					maxRequestsPerSecond));
    		map.from(throttlerProperties::getDrainInterval)
    			.asInt(Duration::toMillis)
    			.to((drainInterval) -> options.add(DefaultDriverOption.REQUEST_THROTTLER_DRAIN_INTERVAL, drainInterval));
    	}
}
