public class test161 {

    @Bean
    		RedisConnectionDetails redisConnectionDetails() {
    			return new RedisConnectionDetails() {
    
    				@Override
    				public Standalone getStandalone() {
    					return new Standalone() {
    
    						@Override
    						public String getHost() {
    							return "localhost";
    						}
    
    						@Override
    						public int getPort() {
    							return 6379;
    						}
    
    					};
    				}
    
    			};
    		}
}
