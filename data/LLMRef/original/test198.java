public class test198 {

    public CacheControl toHttpCacheControl() {
    					PropertyMapper map = PropertyMapper.get();
    					CacheControl control = createCacheControl();
    					map.from(this::getMustRevalidate).whenTrue().toCall(control::mustRevalidate);
    					map.from(this::getNoTransform).whenTrue().toCall(control::noTransform);
    					map.from(this::getCachePublic).whenTrue().toCall(control::cachePublic);
    					map.from(this::getCachePrivate).whenTrue().toCall(control::cachePrivate);
    					map.from(this::getProxyRevalidate).whenTrue().toCall(control::proxyRevalidate);
    					map.from(this::getStaleWhileRevalidate)
    						.whenNonNull()
    						.to((duration) -> control.staleWhileRevalidate(duration.getSeconds(), TimeUnit.SECONDS));
    					map.from(this::getStaleIfError)
    						.whenNonNull()
    						.to((duration) -> control.staleIfError(duration.getSeconds(), TimeUnit.SECONDS));
    					map.from(this::getSMaxAge)
    						.whenNonNull()
    						.to((duration) -> control.sMaxAge(duration.getSeconds(), TimeUnit.SECONDS));
    					// check if cacheControl remained untouched
    					if (control.getHeaderValue() == null) {
    						return null;
    					}
    					return control;
    				}
}
