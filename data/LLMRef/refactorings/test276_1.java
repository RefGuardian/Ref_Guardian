public class test276 {

    private String buildPropertyDeprecations(ItemMetadata... items) throws Exception {
        JSONArray propertiesArray = new JSONArray();
        for (ItemMetadata item : items) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", item.getName());
            if (item.getType() != null) {
                jsonObject.put("type", item.getType());
            }
            ItemDeprecation deprecation = item.getDeprecation();
            if (deprecation != null) {
                JSONObject deprecationJson = new JSONObject();
                String reason = deprecation.getReason();
                if (reason != null) {
                    deprecationJson.put("reason", reason);
                }
                String replacement = deprecation.getReplacement();
                if (replacement != null) {
                    deprecationJson.put("replacement", replacement);
                }
                String since = deprecation.getSince();
                if (since != null) {
                    deprecationJson.put("since", since);
                }
                jsonObject.put("deprecation", deprecationJson);
            }
            propertiesArray.put(jsonObject);

        }
        JSONObject additionalMetadata = new JSONObject();
        additionalMetadata.put("properties", propertiesArray);
        return additionalMetadata.toString();
    }
}
