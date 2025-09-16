public class test169 {

    @Bean
    		MongoConnectionDetails mongoConnectionDetails() {
    			return new MongoConnectionDetails() {
    
    				@Override
    				public ConnectionString getConnectionString() {
    					return new ConnectionString("mongodb://localhost/db");
    				}
    
    				@Override
    				public GridFs getGridFs() {
    					return new GridFs() {
    
    						@Override
    						public String getDatabase() {
    							return "grid-database-1";
    						}
    
    						@Override
    						public String getBucket() {
    							return "connection-details-bucket";
    						}
    
    					};
    				}
    
    			};
    		}
}
