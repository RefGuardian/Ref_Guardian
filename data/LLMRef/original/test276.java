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
    				if (deprecation.getReason() != null) {
    					deprecationJson.put("reason", deprecation.getReason());
    				}
    				if (deprecation.getReplacement() != null) {
    					deprecationJson.put("replacement", deprecation.getReplacement());
    				}
    				if (deprecation.getSince() != null) {
    					deprecationJson.put("since", deprecation.getSince());
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
