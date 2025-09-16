public class test283 {

    @Test
    	void shouldCheckHintProviderFields() {
    		String json = """
    				{
    					"groups": [], "properties": [], "hints": [
    						{
    							"name": "name",
    							"values": [],
    							"providers": extractProviders()
    						}
    					]
    				}""";
    		assertThatException().isThrownBy(() -> read(json))
    			.withMessage(
    					"Expected only keys [name, parameters], but found additional keys [dummy]. Path: .hints.[0].providers.[0]");
    	}

    private List<Provider> extractProviders() {
        return List.of(
                new Provider("name", Map.of("target", "jakarta.servlet.http.HttpServlet"), "dummy")
        );
    }

    static class Provider {
        String name;
        Map<String, String> parameters;
        String dummy;

        public Provider(String name, Map<String, String> parameters, String dummy) {
            this.name = name;
            this.parameters = parameters;
            this.dummy = dummy;
        }
    }
}
