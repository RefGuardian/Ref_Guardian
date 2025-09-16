public class test176 {
    
    private static final String COUCHBASE_CONNECTION_STRING = "couchbase.example.com";
    private static final String COUCHBASE_USERNAME = "user-1";
    private static final String COUCHBASE_PASSWORD = "password-1";

    private CouchbaseConnectionDetails couchbaseConnectionDetails() {
        return new CouchbaseConnectionDetails() {

            @Override
            public String getConnectionString() {
                return COUCHBASE_CONNECTION_STRING;
            }

            @Override
            public String getUsername() {
                return COUCHBASE_USERNAME;
            }

            @Override
            public String getPassword() {
                return COUCHBASE_PASSWORD;
            }

        };
    }
}
