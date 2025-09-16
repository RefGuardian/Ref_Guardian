public class test283 {

    @Test
    	void shouldCheckHintProviderFields() {
    		String json = """
    				{
    					"groups": [], "properties": [], "hints": [
    						{
    							"name": "name",
    							"values": [],
    							"providers": [
    								{
    									"name": "name",
    									"parameters": {
    										"target": "jakarta.servlet.http.HttpServlet"
    									}
    								}
    							]
    						}
    					]
    				}""";
    		assertThatException().isThrownBy(() -> read(json))
    			.withMessage(
    					"Expected only keys [name, parameters], but found additional keys [dummy]. Path: .hints.[0].providers.[0]");
    	}
}
