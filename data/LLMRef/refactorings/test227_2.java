public class test227 {

	private void configureContainer(ContainerProperties container) {
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		Listener properties = this.properties.getListener();
		map.from(properties::getAckMode).to(container::setAckMode);
		map.from(properties::getAsyncAcks).to(container::setAsyncAcks);
		map.from(properties::getClientId).to(container::setClientId);
		map.from(properties::getAckCount).to(container::setAckCount);
		map.from(properties::getAckTime).as(Duration::toMillis).to(container::setAckTime);
		map.from(properties::getPollTimeout).as(Duration::toMillis).to(container::setPollTimeout);
		map.from(properties::getNoPollThreshold).to(container::setNoPollThreshold);
		map.from(properties.getIdleBetweenPolls()).as(Duration::toMillis).to(container::setIdleBetweenPolls);
		map.from(properties::getIdleEventInterval).as(Duration::toMillis).to(container::setIdleEventInterval);
		map.from(properties::getIdlePartitionEventInterval)
			.as(Duration::toMillis)
			.to(container::setIdlePartitionEventInterval);
		map.from(properties::getMonitorInterval)
			.as(Duration::getSeconds)
			.as(Number::intValue)
			.to(container::setMonitorInterval);
		map.from(properties::getLogContainerConfig).to(container::setLogContainerConfig);
		map.from(properties::isMissingTopicsFatal).to(container::setMissingTopicsFatal);
		map.from(properties::isImmediateStop).to(container::setStopImmediate);
		map.from(properties::isObservationEnabled).to(container::setObservationEnabled);
		map.from(properties::getAuthExceptionRetryInterval).to(container::setAuthExceptionRetryInterval);
		map.from(this.transactionManager).to(container::setKafkaAwareTransactionManager);
		map.from(this.rebalanceListener).to(container::setConsumerRebalanceListener);
		map.from(this.listenerTaskExecutor).to(container::setListenerTaskExecutor);
	}

	// Extracted Method to set properties from listener
	private Listener getListener() {
		return this.properties.getListener();
	}
}
