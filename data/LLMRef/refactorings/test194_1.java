public class test194 {

    private final PropertyMapper map;

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(this.serverProperties::getPort).to(factory::setPort);
        map.from(this.serverProperties::getAddress).to(factory::setAddress);
        map.from(this.serverProperties.getServlet()::getContextPath).to(factory::setContextPath);
        map.from(this.serverProperties.getServlet()::getApplicationDisplayName).to(factory::setDisplayName);
        map.from(this.serverProperties.getServlet()::isRegisterDefaultServlet).to(factory::setRegisterDefaultServlet);
        map.from(this.serverProperties.getServlet()::getSession).to(factory::setSession);
        map.from(this.serverProperties::getSsl).to(factory::setSsl);
        map.from(this.serverProperties.getServlet()::getJsp).to(factory::setJsp);
        map.from(this.serverProperties::getCompression).to(factory::setCompression);
        map.from(this.serverProperties::getHttp2).to(factory::setHttp2);
        map.from(this.serverProperties.getServerHeader).to(factory::setServerHeader);
        map.from(this.serverProperties.getServlet()::getContextParameters).to(factory::setInitParameters);
        map.from(this.serverProperties.getShutdown()).to(factory::setShutdown);
        map.from(() -> this.sslBundles).to(factory::setSslBundles);
        map.from(() -> this.cookieSameSiteSuppliers)
                .whenNot(CollectionUtils::isEmpty)
                .to(factory::setCookieSameSiteSuppliers);
        map.from(this.serverProperties::getMimeMappings).to(factory::addMimeMappings);
        this.webListenerRegistrars.forEach((registrar) -> registrar.register(factory));
    }
}
