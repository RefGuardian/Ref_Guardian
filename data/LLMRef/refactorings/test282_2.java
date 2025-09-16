public class test282 {

    @Test
    	void shouldCheckHintValueFields() {
    		String json = """
    				{
    					"groups": [], "properties": [], "hints": [
    						{
    							"name": "name",
    							"values": getValues(),
    							"providers": []
    						}
    					]
    				}""";
    		assertThatException().isThrownBy(() -> read(json))
    			.withMessage(
    					"Expected only keys [description, value], but found additional keys [dummy]. Path: .hints.[0].values.[0]");
    	}
    
    private String getValues() {
        return """
    								{
    									"value": "value",
    									"description": "some description",
    									"dummy": "dummy"
    								}
                                """;
    }
}
