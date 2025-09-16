public class test199 {

    private static final String DEFAULT_CODEC_TYPE = "DefaultPartHttpMessageReader";
    
    @Bean
    @Order(0)
    CodecCustomizer defaultPartHttpMessageReaderCustomizer(ReactiveMultipartProperties multipartProperties) {
        return (configurer) -> configurer.defaultCodecs().configureDefaultCodec((codec) -> {
            if (codec.getClass().getSimpleName().equals(DEFAULT_CODEC_TYPE)) {
                PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
                map.from(multipartProperties::getMaxInMemorySize)
                    .asInt(DataSize::toBytes)
                    .to(((DefaultPartHttpMessageReader)codec)::setMaxInMemorySize);
                map.from(multipartProperties::getMaxHeadersSize)
                    .asInt(DataSize::toBytes)
                    .to(((DefaultPartHttpMessageReader)codec)::setMaxHeadersSize);
                map.from(multipartProperties::getMaxDiskUsagePerPart)
                    .as(DataSize::toBytes)
                    .to(((DefaultPartHttpMessageReader)codec)::setMaxDiskUsagePerPart);
                map.from(multipartProperties::getMaxParts).to(((DefaultPartHttpMessageReader)codec)::setMaxParts);
                map.from(multipartProperties::getFileStorageDirectory)
                    .as(Paths::get)
                    .to((dir) -> configureFileStorageDirectory((DefaultPartHttpMessageReader)codec, dir));
                map.from(multipartProperties::getHeadersCharset).to(((DefaultPartHttpMessageReader)codec)::setHeadersCharset);
            }
            else if (codec instanceof PartEventHttpMessageReader partEventHttpMessageReader) {
                PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
                map.from(multipartProperties::getMaxInMemorySize)
                    .asInt(DataSize::toBytes)
                    .to(partEventHttpMessageReader::setMaxInMemorySize);
                map.from(multipartProperties::getMaxHeadersSize)
                    .asInt(DataSize::toBytes)
                    .to(partEventHttpMessageReader::setMaxHeadersSize);
                map.from(multipartProperties::getMaxDiskUsagePerPart)
                    .as(DataSize::toBytes)
                    .to(partEventHttpMessageReader::setMaxPartSize);
                map.from(multipartProperties::getMaxParts).to(partEventHttpMessageReader::setMaxParts);
                map.from(multipartProperties::getHeadersCharset).to(partEventHttpMessageReader::setHeadersCharset);
            }
        });
    }
}
