public class test78 {

    private static final String SPRING_WEBFLUX_MULTIPART_MAX_IN_MEMORY_SIZE = "spring.webflux.multipart.max-in-memory-size=1GB";
    private static final String SPRING_WEBFLUX_MULTIPART_MAX_HEADERS_SIZE = "spring.webflux.multipart.max-headers-size=16KB";
    private static final String SPRING_WEBFLUX_MULTIPART_MAX_DISK_USAGE_PER_PART = "spring.webflux.multipart.max-disk-usage-per-part=3GB";
    private static final String SPRING_WEBFLUX_MULTIPART_MAX_PARTS = "spring.webflux.multipart.max-parts=7";
    private static final String SPRING_WEBFLUX_MULTIPART_HEADERS_CHARSET = "spring.webflux.multipart.headers-charset:UTF_16";

    @Test
    void shouldConfigureMultipartPropertiesForDefaultReader() {
        this.contextRunner
            .withPropertyValues(SPRING_WEBFLUX_MULTIPART_MAX_IN_MEMORY_SIZE,
                    SPRING_WEBFLUX_MULTIPART_MAX_HEADERS_SIZE,
                    SPRING_WEBFLUX_MULTIPART_MAX_DISK_USAGE_PER_PART, SPRING_WEBFLUX_MULTIPART_MAX_PARTS,
                    SPRING_WEBFLUX_MULTIPART_HEADERS_CHARSET)
            .run((context) -> {
                CodecCustomizer customizer = context.getBean(CodecCustomizer.class);
                DefaultServerCodecConfigurer configurer = new DefaultServerCodecConfigurer();
                customizer.customize(configurer);
                DefaultPartHttpMessageReader partReader = getDefaultPartReader(configurer);
                assertThat(partReader).hasFieldOrPropertyWithValue("maxParts", 7);
                assertThat(partReader).hasFieldOrPropertyWithValue("maxHeadersSize",
                        Math.toIntExact(DataSize.ofKilobytes(16).toBytes()));
                assertThat(partReader).hasFieldOrPropertyWithValue("headersCharset", StandardCharsets.UTF_16);
                assertThat(partReader).hasFieldOrPropertyWithValue("maxInMemorySize",
                        Math.toIntExact(DataSize.ofGigabytes(1).toBytes()));
                assertThat(partReader).hasFieldOrPropertyWithValue("maxDiskUsagePerPart",
                        DataSize.ofGigabytes(3).toBytes());
            });
    }
}
