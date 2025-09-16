public class test231 {

    private Config mapConfig(CassandraConnectionDetails connectionDetails) {
        CassandraDriverOptions options = createCassandraDriverOptions();
        mapConnectionDetails(connectionDetails, options);
        return options.build();
    }

    private CassandraDriverOptions createCassandraDriverOptions() {
        CassandraDriverOptions options = new CassandraDriverOptions();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.properties.getSessionName())
                .whenHasText()
                .to((sessionName) -> options.add(DefaultDriverOption.SESSION_NAME, sessionName));
        map.from(connectionDetails.getUsername())
                .to((value) -> options.add(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, value)
                        .add(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, connectionDetails.getPassword()));
        map.from(this.properties::getCompression)
                .to((compression) -> options.add(DefaultDriverOption.PROTOCOL_COMPRESSION, compression));
        mapPoolingOptions(options);
        mapRequestOptions(options);
        mapControlConnectionOptions(options);
        map.from(mapContactPoints(connectionDetails))
                .to((contactPoints) -> options.add(DefaultDriverOption.CONTACT_POINTS, contactPoints));
        map.from(connectionDetails.getLocalDatacenter())
                .whenHasText()
                .to((localDatacenter) -> options.add(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, localDatacenter));
        return options;
    }

    private void mapConnectionDetails(CassandraConnectionDetails connectionDetails, CassandraDriverOptions options) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.properties.getSessionName())
                .whenHasText()
                .to((sessionName) -> options.add(DefaultDriverOption.SESSION_NAME, sessionName));
        map.from(connectionDetails.getUsername())
                .to((value) -> options.add(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, value)
                        .add(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, connectionDetails.getPassword()));
        map.from(this.properties::getCompression)
                .to((compression) -> options.add(DefaultDriverOption.PROTOCOL_COMPRESSION, compression));
        map.from(mapContactPoints(connectionDetails))
                .to((contactPoints) -> options.add(DefaultDriverOption.CONTACT_POINTS, contactPoints));
        map.from(connectionDetails.getLocalDatacenter())
                .whenHasText()
                .to((localDatacenter) -> options.add(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, localDatacenter));
    }

}
