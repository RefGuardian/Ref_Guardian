public class test30 {

    @Override
    	public LiquibaseConnectionDetails getConnectionDetails(JdbcConnectionDetails input) {
    		return new LiquibaseConnectionDetails() {
    
    			@Override
    			public String getUsername() {
    				return input.getUsername();
    			}
    
    			@Override
    			public String getPassword() {
    				return input.getPassword();
    			}
    
    			@Override
    			public String getJdbcUrl() {
    				return input.getJdbcUrl();
    			}
    
    			@Override
    			public String getDriverClassName() {
    				return input.getDriverClassName();
    			}
    
    		};
    	}
}
