public class test162 {

    @Bean
    		RedisConnectionDetails redisConnectionDetails() {
    			return new RedisConnectionDetails() {
    
    				@Override
    				public String getUsername() {
    					return "user-1";
    				}
    
    				@Override
    				public String getPassword() {
    					return "password-1";
    				}
    
    				@Override
    				public Standalone getStandalone() {
    					return new Standalone() {
    
    						@Override
    						public int getDatabase() {
    							return 1;
    						}
    
    						@Override
    						public String getHost() {
    							return "redis.example.com";
    						}
    
    						@Override
    						public int getPort() {
    							return 16379;
    						}
    
    					};
    				}
    
    			};
    		}
}
