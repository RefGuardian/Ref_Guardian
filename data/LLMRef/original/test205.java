public class test205 {

    private void mapUndertowProperties(ConfigurableUndertowWebServerFactory factory, ServerOptions serverOptions) {
    		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
    		Undertow properties = this.serverProperties.getUndertow();
    		map.from(properties::getBufferSize).whenNonNull().asInt(DataSize::toBytes).to(factory::setBufferSize);
    		ServerProperties.Undertow.Threads threadProperties = properties.getThreads();
    		map.from(threadProperties::getIo).to(factory::setIoThreads);
    		map.from(threadProperties::getWorker).to(factory::setWorkerThreads);
    		map.from(properties::getDirectBuffers).to(factory::setUseDirectBuffers);
    		map.from(properties::getMaxHttpPostSize)
    			.as(DataSize::toBytes)
    			.when(this::isPositive)
    			.to(serverOptions.option(UndertowOptions.MAX_ENTITY_SIZE));
    		map.from(properties::getMaxParameters).to(serverOptions.option(UndertowOptions.MAX_PARAMETERS));
    		map.from(properties::getMaxHeaders).to(serverOptions.option(UndertowOptions.MAX_HEADERS));
    		map.from(properties::getMaxCookies).to(serverOptions.option(UndertowOptions.MAX_COOKIES));
    		mapSlashProperties(properties, serverOptions);
    		map.from(properties::isDecodeUrl).to(serverOptions.option(UndertowOptions.DECODE_URL));
    		map.from(properties::getUrlCharset).as(Charset::name).to(serverOptions.option(UndertowOptions.URL_CHARSET));
    		map.from(properties::isAlwaysSetKeepAlive).to(serverOptions.option(UndertowOptions.ALWAYS_SET_KEEP_ALIVE));
    		map.from(properties::getNoRequestTimeout)
    			.asInt(Duration::toMillis)
    			.to(serverOptions.option(UndertowOptions.NO_REQUEST_TIMEOUT));
    		map.from(properties.getOptions()::getServer).to(serverOptions.forEach(serverOptions::option));
    		SocketOptions socketOptions = new SocketOptions(factory);
    		map.from(properties.getOptions()::getSocket).to(socketOptions.forEach(socketOptions::option));
    	}
}
