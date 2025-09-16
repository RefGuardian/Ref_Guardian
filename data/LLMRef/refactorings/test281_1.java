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
    							"deprecation": getDeprecationJson(),
    							"deprecated": true
    						}
    					], "hints": []
    				}""";
    		assertThatException().isThrownBy(() -> read(json))
    			.withMessage(
    					"Expected only keys [level, reason, replacement, since], but found additional keys [dummy]. Path: .properties.[0].deprecation");
    	}

    private String getDeprecationJson() {
        return """
            {
                "level": "warning",
                "reason": "some reason",
                "replacement": "name-new",
                "since": "v17",
                "dummy": "dummy"
            }""";
    }
}
