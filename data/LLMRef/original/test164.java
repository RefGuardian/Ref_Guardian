public class test164 {

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
    				public Sentinel getSentinel() {
    					return new Sentinel() {
    
    						@Override
    						public int getDatabase() {
    							return 1;
    						}
    
    						@Override
    						public String getMaster() {
    							return "master.redis.example.com";
    						}
    
    						@Override
    						public List<Node> getNodes() {
    							return List.of(new Node("node-1", 12345));
    						}
    
    						@Override
    						public String getUsername() {
    							return "sentinel-1";
    						}
    
    						@Override
    						public String getPassword() {
    							return "secret-1";
    						}
    
    					};
    				}
    
    			};
    		}
}
