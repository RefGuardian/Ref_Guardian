public class test176 {

    private CouchbaseConnectionDetails couchbaseConnectionDetails() {
    	return getNewDetails();
    }
    
    private CouchbaseConnectionDetails getNewDetails() {
    	return new CouchbaseConnectionDetails() {
    
    		@Override
    		public String getConnectionString() {
    			return "couchbase.example.com";
    		}
    
    		@Override
    		public String getUsername() {
    			return "user-1";
    		}
    
    		@Override
    		public String getPassword() {
    			return "password-1";
    		}
    
    	};
    }
}
