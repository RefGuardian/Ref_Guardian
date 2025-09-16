public class test281 {

    @Test
    	void shouldCheckPropertyDeprecationFields() {
    		String json = """
    				{
    					"groups": [], "properties": [
    						{
    							"name": "name",
    							"type": "java.lang.String",
    							"description": "Some description",
    							"sourceType": "java.lang.String",
    							"defaultValue": "value",
    							"deprecation": createDeprecation(),
    							"deprecated": true
    						}
    					], "hints": []
    				}""";
    		assertThatException().isThrownBy(() -> read(json))
    			.withMessage(
    					"Expected only keys [level, reason, replacement, since], but found additional keys [dummy]. Path: .properties.[0].deprecation");
    	}

    private Map<String, Object> createDeprecation() {
        Map<String, Object> deprecation = new HashMap<>();
        deprecation.put("level", "warning");
        deprecation.put("reason", "some reason");
        deprecation.put("replacement", "name-new");
        deprecation.put("since", "v17");
        deprecation.put("dummy", "dummy");
        return deprecation;
    }
}
