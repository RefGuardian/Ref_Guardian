public class test247 {

    /**
    		 * Creates a new instance with the given host, port, database and SSL bundle.
    		 * @param host the host
    		 * @param port the port
    		 * @param database the database
    		 * @param sslBundle the SSL bundle
    		 * @return the new instance
    		 * @since 3.5.0
    		 */
    		static Standalone of(String host, int port, int database, SslBundle sslBundle) {
    			Assert.hasLength(host, "'host' must not be empty");
    			return new Standalone() {
    
    				@Override
    				public String getHost() {
    					return host;
    				}
    
    				@Override
    				public int getPort() {
    					return port;
    				}
    
    				@Override
    				public int getDatabase() {
    					return database;
    				}
    
    				@Override
    				public SslBundle getSslBundle() {
    					return sslBundle;
    				}
    			};
    		}
}
