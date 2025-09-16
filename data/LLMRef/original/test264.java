public class test264 {

    /**
    	 * Return if this source accepts the given connection.
    	 * @param requiredConnectionName the required connection name or {@code null}
    	 * @param requiredContainerType the required container type
    	 * @param requiredConnectionDetailsType the required connection details type
    	 * @return if the connection is accepted by this source
    	 * @since 3.4.0
    	 */
    	public boolean accepts(String requiredConnectionName, Class<?> requiredContainerType,
    			Class<?> requiredConnectionDetailsType) {
    		if (StringUtils.hasText(requiredConnectionName)
    				&& !requiredConnectionName.equalsIgnoreCase(this.connectionName)) {
    			logger.trace(LogMessage
    				.of(() -> "%s not accepted as source connection name '%s' does not match required connection name '%s'"
    					.formatted(this, this.connectionName, requiredConnectionName)));
    			return false;
    		}
    		if (!requiredContainerType.isAssignableFrom(this.containerType)) {
    			logger.trace(LogMessage.of(() -> "%s not accepted as source container type %s is not assignable from %s"
    				.formatted(this, this.containerType.getName(), requiredContainerType.getName())));
    			return false;
    		}
    		if (!this.connectionDetailsTypes.isEmpty() && this.connectionDetailsTypes.stream()
    			.noneMatch((candidate) -> candidate.isAssignableFrom(requiredConnectionDetailsType))) {
    			logger.trace(LogMessage
    				.of(() -> "%s not accepted as source connection details types %s has no element assignable from %s"
    					.formatted(this, this.connectionDetailsTypes.stream().map(Class::getName).toList(),
    							requiredConnectionDetailsType.getName())));
    			return false;
    		}
    		logger.trace(
    				LogMessage.of(() -> "%s accepted for connection name '%s' container type %s, connection details type %s"
    					.formatted(this, requiredConnectionName, requiredContainerType.getName(),
    							requiredConnectionDetailsType.getName())));
    		return true;
    	}
}
