public class test199 {

    @Bean
    	@Order(0)
    	CodecCustomizer defaultPartHttpMessageReaderCustomizer(ReactiveMultipartProperties multipartProperties) {
    		return (configurer) -> configurer.defaultCodecs().configureDefaultCodec((codec) -> {
    			if (codec instanceof DefaultPartHttpMessageReader defaultPartHttpMessageReader) {
    				PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
    				map.from(multipartProperties::getMaxInMemorySize)
    					.asInt(DataSize::toBytes)
    					.to(defaultPartHttpMessageReader::setMaxInMemorySize);
    				map.from(multipartProperties::getMaxHeadersSize)
    					.asInt(DataSize::toBytes)
    					.to(defaultPartHttpMessageReader::setMaxHeadersSize);
    				map.from(multipartProperties::getMaxDiskUsagePerPart)
    					.as(DataSize::toBytes)
    					.to(defaultPartHttpMessageReader::setMaxDiskUsagePerPart);
    				map.from(multipartProperties::getMaxParts).to(defaultPartHttpMessageReader::setMaxParts);
    				map.from(multipartProperties::getFileStorageDirectory)
    					.as(Paths::get)
    					.to((dir) -> configureFileStorageDirectory(defaultPartHttpMessageReader, dir));
    				map.from(multipartProperties::getHeadersCharset).to(defaultPartHttpMessageReader::setHeadersCharset);
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
