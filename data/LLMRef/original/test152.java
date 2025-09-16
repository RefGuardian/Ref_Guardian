public class test152 {

    @Bean
    		LiquibaseConnectionDetails liquibaseConnectionDetails() {
    			return new LiquibaseConnectionDetails() {
    
    				@Override
    				public String getJdbcUrl() {
    					return "jdbc:postgresql://database.example.com:12345/database-1";
    				}
    
    				@Override
    				public String getUsername() {
    					return "user-1";
    				}
    
    				@Override
    				public String getPassword() {
    					return "secret-1";
    				}
    
    			};
    		}
}
