public class test231 {

    private Config mapConfig(CassandraConnectionDetails connectionDetails) {
        CassandraDriverOptions options = new CassandraDriverOptions();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.properties.getSessionName())
            .whenHasText()
            .to(this::addSessionOptions);
        map.from(connectionDetails.getUsername())
            .to(this::addAuthOptions);
        map.from(this.properties::getCompression)
            .to((compression) -> options.add(DefaultDriverOption.PROTOCOL_COMPRESSION, compression));
        mapConnectionOptions(options);
        mapPoolingOptions(options);
        mapRequestOptions(options);
        mapControlConnectionOptions(options);
        map.from(mapContactPoints(connectionDetails))
            .to((contactPoints) -> options.add(DefaultDriverOption.CONTACT_POINTS, contactPoints));
        map.from(connectionDetails.getLocalDatacenter())
            .whenHasText()
            .to((localDatacenter) -> options.add(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, localDatacenter));
        return options.build();
    }

    private void addSessionOptions(String sessionName) {
        options.add(DefaultDriverOption.SESSION_NAME, sessionName);
    }

    private void addAuthOptions(String value) {
        options.add(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, value)
                .add(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, connectionDetails.getPassword());
    }
}
