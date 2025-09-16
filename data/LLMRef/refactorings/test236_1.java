public class test236 {

    @Override
    public ConnectionString getConnectionString() {
        if (this.properties.getUri() != null) {
            return new ConnectionString(this.properties.getUri());
        }
        StringBuilder builder = new StringBuilder(getProtocol()).append("://");
        buildConnectionUrl(builder);
        return new ConnectionString(builder.toString());
    }

    private void buildConnectionUrl(StringBuilder builder) {
        if (this.properties.getUsername() != null) {
            builder.append(encode(this.properties.getUsername()));
            builder.append(":");
            if (this.properties.getPassword() != null) {
                builder.append(encode(this.properties.getPassword()));
            }
            builder.append("@");
        }
        builder.append((this.properties.getHost() != null) ? this.properties.getHost() : "localhost");
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
    }
}
