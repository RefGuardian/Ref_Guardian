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
        if (hasTextAndNotEquals(requiredConnectionName)) {
            return false;
        }
        if (!isAssignableFrom(requiredContainerType)) {
            return false;
        }
        if (isEmptyAndNotAssignable(requiredConnectionDetailsType)) {
            return false;
        }
        acceptedForConnection(requiredConnectionName, requiredContainerType, requiredConnectionDetailsType);
        return true;
    }

    private boolean hasTextAndNotEquals(String requiredConnectionName) {
        return StringUtils.hasText(requiredConnectionName) && !requiredConnectionName.equalsIgnoreCase(this.connectionName);
    }

    private boolean isAssignableFrom(Class<?> requiredContainerType) {
        return !requiredContainerType.isAssignableFrom(this.containerType);
    }

    private boolean isEmptyAndNotAssignable(Class<?> requiredConnectionDetailsType) {
        return !this.connectionDetailsTypes.isEmpty() && this.connectionDetailsTypes.stream()
                .noneMatch((candidate) -> candidate.isAssignableFrom(requiredConnectionDetailsType));
    }

    private void acceptedForConnection(String requiredConnectionName, Class<?> requiredContainerType, Class<?> requiredConnectionDetailsType) {
        logger.trace(
                LogMessage.of(() -> "%s accepted for connection name '%s' container type %s, connection details type %s"
                        .formatted(this, requiredConnectionName, requiredContainerType.getName(),
                                requiredConnectionDetailsType.getName())));
    }
}
