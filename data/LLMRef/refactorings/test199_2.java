public class test199 {

    @Bean
    @Order(0)
    CodecCustomizer defaultPartHttpMessageReaderCustomizer(ReactiveMultipartProperties multipartProperties) {
        return (configurer) -> configurer.defaultCodecs().configureDefaultCodec((codec) -> {
            PropertyMapper map = PropertyMapper.getFirst().alwaysApplyingWhenNonNull();
            map.from(multipartProperties::getMaxInMemorySize)
                .asInt(DataSize::toBytes)
                .to((defaultPartHttpMessageReader) -> {
                    defaultPartHttpMessageReader.setMaxInMemorySize(multipartProperties.getMaxInMemorySize().toBytes());
                });
            map.from(multipartProperties::getMaxHeadersSize)
                .asInt(DataSize::toBytes)
                .to((defaultPartHttpMessageReader) -> {
                    defaultPartHttpMessageReader.setMaxHeadersSize(multipartProperties.getMaxHeadersSize().toBytes());
                });
            map.from(multipartProperties::getMaxDiskUsagePerPart)
                .as(DataSize::toBytes)
                .to((defaultPartHttpMessageReader) -> {
                    defaultPartHttpMessageReader.setMaxDiskUsagePerPart(multipartProperties.getMaxDiskUsagePerPart().toBytes());
                });
            map.from(multipartProperties::getMaxParts)
                .to((defaultPartHttpMessageReader) -> {
                    defaultPartHttpMessageReader.setMaxParts(multipartProperties.getMaxParts());
                });
            map.from(multipartProperties::getFileStorageDirectory)
                .as(Paths::get)
                .to((dir) -> configureFileStorageDirectory(defaultPartHttpMessageReader, dir));
            map.from(multipartProperties::getHeadersCharset)
                .to((defaultPartHttpMessageReader) -> {
                    defaultPartHttpMessageReader.setHeadersCharset(multipartProperties.getHeadersCharset());
                });
        });
    }
}
