public class test126 {

    private CassandraConnectionDetails cassandraConnectionDetails() {
        return createCassandraConnectionDetails();
    }

    private static CassandraConnectionDetails createCassandraConnectionDetails() {
        return new CassandraConnectionDetails() {

            @Override
            public List<Node> getContactPoints() {
                return List.of(new Node("cassandra.example.com", 9042));
            }

            @Override
            public String getUsername() {
                return "user-1";
            }

            @Override
            public String getPassword() {
                return "secret-1";
            }

            @Override
            public String getLocalDatacenter() {
                return "datacenter-1";
            }

        };
    }
}
