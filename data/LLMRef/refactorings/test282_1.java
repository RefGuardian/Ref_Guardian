public class test282 {

    @Test
    	void shouldCheckHintValueFields() {
    		String json = """
    				{
    					"groups": [], "properties": [], "hints": [
    						{
    							"name": "name",
    							"values": [
    								{
    									"value": "value",
    									"description": "some description"
    								}
    							],
    							"providers": []
    						}
    					]
    				}""";
    		assertThatException().isThrownBy(() -> read(json))
    			.withMessage(
    					"Expected only keys [description, value], but found additional keys [dummy]. Path: .hints.[0].values.[0]");
    	}
}
