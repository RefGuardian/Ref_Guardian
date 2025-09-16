public class test79 {

    private static final String SPRING_WEBFLUX_MULTIPART_MAX_IN_MEMORY_SIZE = "spring.webflux.multipart.max-in-memory-size";
    private static final String SPRING_WEBFLUX_MULTIPART_MAX_HEADERS_SIZE = "spring.webflux.multipart.max-headers-size";
    private static final String SPRING_WEBFLUX_MULTIPART_MAX_DISK_USAGE_PER_PART = "spring.webflux.multipart.max-disk-usage-per-part";
    private static final String SPRING_WEBFLUX_MULTIPART_MAX_PARTS = "spring.webflux.multipart.max-parts";
    private static final String SPRING_WEBFLUX_MULTIPART_HEADERS_CHARSET = "spring.webflux.multipart.headers-charset";

    @Test
    void shouldConfigureMultipartPropertiesForPartEventReader() {
        this.contextRunner
            .withPropertyValues(SPRING_WEBFLUX_MULTIPART_MAX_IN_MEMORY_SIZE + "=1GB",
                    SPRING_WEBFLUX_MULTIPART_MAX_HEADERS_SIZE + "=16KB",
                    SPRING_WEBFLUX_MULTIPART_MAX_DISK_USAGE_PER_PART + "=3GB", SPRING_WEBFLUX_MULTIPART_MAX_PARTS + "=7",
                    SPRING_WEBFLUX_MULTIPART_HEADERS_CHARSET + ":UTF_16")
            .run((context) -> {
                CodecCustomizer customizer = context.getBean(CodecCustomizer.class);
                DefaultServerCodecConfigurer configurer = new DefaultServerCodecConfigurer();
                customizer.customize(configurer);
                PartEventHttpMessageReader partReader = getPartEventReader(configurer);
                assertThat(partReader).hasFieldOrPropertyWithValue("maxParts", 7);
                assertThat(partReader).hasFieldOrPropertyWithValue("maxHeadersSize",
                        Math.toIntExact(DataSize.ofKilobytes(16).toBytes()));
                assertThat(partReader).hasFieldOrPropertyWithValue("headersCharset", StandardCharsets.UTF_16);
                assertThat(partReader).hasFieldOrPropertyWithValue("maxInMemorySize",
                        Math.toIntExact(DataSize.ofGigabytes(1).toBytes()));
                assertThat(partReader).hasFieldOrPropertyWithValue("maxPartSize", DataSize.ofGigabytes(3).toBytes());
            });
    }
}
