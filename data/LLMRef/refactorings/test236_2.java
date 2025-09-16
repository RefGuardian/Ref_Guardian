public class test236 {

    @Override
    public ConnectionString getConnectionString() {
        // protocol://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database.collection][?options]]
        if (this.properties.getUri() != null) {
            return new ConnectionString(this.properties.getUri());
        }
        String protocol = getProtocol();
        StringBuilder builder = new StringBuilder(protocol).append("://");
        if (this.properties.getUsername() != null) {
            String encodedUsername = encode(this.properties.getUsername());
            builder.append(encodedUsername);
            builder.append(":");
            if (this.properties.getPassword() != null) {
                String encodedPassword = encode(this.properties.getPassword());
                builder.append(encodedPassword);
            }
            builder.append("@");
        }
        String host = this.properties.getHost() != null ? this.properties.getHost() : "localhost";
        builder.append(host);
        if (this.properties.getPort() != null) {
            builder.append(":");
            builder.append(this.properties.getPort());
        }
        if (this.properties.getAdditionalHosts() != null) {
            builder.append(",");
            builder.append(String.join(",", this.properties.getAdditionalHosts()));
        }
        builder.append("/");
        builder.append(this.properties.getMongoClientDatabase());
        List<String> options = getOptions();
        if (!options.isEmpty()) {
            builder.append("?");
            builder.append(String.join("&", options));
        }
        return new ConnectionString(builder.toString());
    }
}
