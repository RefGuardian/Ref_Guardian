public class test169 {

    @Bean
    MongoConnectionDetails mongoConnectionDetails() {
        return createMongoConnectionDetails();
    }

    private MongoConnectionDetails createMongoConnectionDetails() {
        return new MongoConnectionDetails() {
    
            @Override
            public ConnectionString getConnectionString() {
                return new ConnectionString("mongodb://localhost/db");
            }
    
            @Override
            public GridFs getGridFs() {
                return createGridFs();
            }
    
        };
    }

    private GridFs createGridFs() {
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
}
