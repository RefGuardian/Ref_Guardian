public class test174 {

    @Bean
    		ElasticsearchConnectionDetails elasticsearchConnectionDetails() {
    			return new ElasticsearchConnectionDetails() {
    
    				@Override
    				public List<Node> getNodes() {
    					return List
    						.of(new Node("elastic.example.com", 9200, Protocol.HTTP, "node-user-1", "node-password-1"));
    				}
    
    				@Override
    				public String getUsername() {
    					return "user-1";
    				}
    
    				@Override
    				public String getPassword() {
    					return "password-1";
    				}
    
    				@Override
    				public String getPathPrefix() {
    					return "/some-path";
    				}
    
    			};
    		}
}
