public class test125 {

    private static final String CONFIG_LOCATION = "org/springframework/boot/autoconfigure/cassandra/override-defaults.conf";

    @Test // gh-31238
    void driverConfigLoaderWithConfigOverridesDefaults() {
        this.contextRunner.withPropertyValues("spring.cassandra.config=" + CONFIG_LOCATION).run((context) -> {
            DriverExecutionProfile actual = context.getBean(DriverConfigLoader.class)
                    .getInitialConfig()
                    .getDefaultProfile();
            assertThat(actual.getString(DefaultDriverOption.SESSION_NAME)).isEqualTo("advanced session");
            assertThat(actual.getDuration(DefaultDriverOption.REQUEST_TIMEOUT)).isEqualTo(Duration.ofSeconds(2));
            assertThat(actual.getStringList(DefaultDriverOption.CONTACT_POINTS))
                    .isEqualTo(Collections.singletonList("1.2.3.4:5678"));
            assertThat(actual.getBoolean(DefaultDriverOption.RESOLVE_CONTACT_POINTS)).isFalse();
            assertThat(actual.getInt(DefaultDriverOption.REQUEST_PAGE_SIZE)).isEqualTo(11);
            assertThat(actual.getString(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER)).isEqualTo("datacenter1");
            assertThat(actual.getInt(DefaultDriverOption.REQUEST_THROTTLER_MAX_CONCURRENT_REQUESTS)).isEqualTo(22);
            assertThat(actual.getInt(DefaultDriverOption.REQUEST_THROTTLER_MAX_REQUESTS_PER_SECOND)).isEqualTo(33);
            assertThat(actual.getInt(DefaultDriverOption.REQUEST_THROTTLER_MAX_QUEUE_SIZE)).isEqualTo(44);
            assertThat(actual.getDuration(DefaultDriverOption.CONTROL_CONNECTION_TIMEOUT))
                    .isEqualTo(Duration.ofMillis(5555));
            assertThat(actual.getString(DefaultDriverOption.PROTOCOL_COMPRESSION)).isEqualTo("SNAPPY");
        });
    }
}
