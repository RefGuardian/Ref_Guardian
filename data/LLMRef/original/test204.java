public class test204 {

    private void customizeAccessLog(ConfigurableTomcatWebServerFactory factory) {
    		ServerProperties.Tomcat tomcatProperties = this.serverProperties.getTomcat();
    		AccessLogValve valve = new AccessLogValve();
    		PropertyMapper map = PropertyMapper.get();
    		Accesslog accessLogConfig = tomcatProperties.getAccesslog();
    		map.from(accessLogConfig.getConditionIf()).to(valve::setConditionIf);
    		map.from(accessLogConfig.getConditionUnless()).to(valve::setConditionUnless);
    		map.from(accessLogConfig.getPattern()).to(valve::setPattern);
    		map.from(accessLogConfig.getDirectory()).to(valve::setDirectory);
    		map.from(accessLogConfig.getPrefix()).to(valve::setPrefix);
    		map.from(accessLogConfig.getSuffix()).to(valve::setSuffix);
    		map.from(accessLogConfig.getEncoding()).whenHasText().to(valve::setEncoding);
    		map.from(accessLogConfig.getLocale()).whenHasText().to(valve::setLocale);
    		map.from(accessLogConfig.isCheckExists()).to(valve::setCheckExists);
    		map.from(accessLogConfig.isRotate()).to(valve::setRotatable);
    		map.from(accessLogConfig.isRenameOnRotate()).to(valve::setRenameOnRotate);
    		map.from(accessLogConfig.getMaxDays()).to(valve::setMaxDays);
    		map.from(accessLogConfig.getFileDateFormat()).to(valve::setFileDateFormat);
    		map.from(accessLogConfig.isIpv6Canonical()).to(valve::setIpv6Canonical);
    		map.from(accessLogConfig.isRequestAttributesEnabled()).to(valve::setRequestAttributesEnabled);
    		map.from(accessLogConfig.isBuffered()).to(valve::setBuffered);
    		factory.addEngineValves(valve);
    	}
}
