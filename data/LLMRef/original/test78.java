public class test78 {

    @Test
    	void shouldConfigureMultipartPropertiesForDefaultReader() {
    		this.contextRunner
    			.withPropertyValues("spring.webflux.multipart.max-in-memory-size=1GB",
    					"spring.webflux.multipart.max-headers-size=16KB",
    					"spring.webflux.multipart.max-disk-usage-per-part=3GB", "spring.webflux.multipart.max-parts=7",
    					"spring.webflux.multipart.headers-charset:UTF_16")
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
