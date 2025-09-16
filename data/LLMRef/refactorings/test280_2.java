public class test280 {

    private static final String JSON = """
            {
                "groups": [], "properties": [
                    {
                        "name": "name",
                        "type": "java.lang.String",
                        "description": "Some description",
                        "sourceType": "java.lang.String",
                        "defaultValue": "value",
                        "deprecation": {
                            "level": "warning",
                            "reason": "some reason",
                            "replacement": "name-new",
                            "since": "v17"
                        },
                        "deprecated": true,
                        "dummy": "dummy"
                    }
                ], "hints": []
            }"";

    @Test
    void shouldCheckPropertyFields() {
        assertThatException().isThrownBy(() -> read(JSON))
                .withMessage(
                        "Expected only keys [defaultValue, deprecated, deprecation, description, name, sourceType, type], but found additional keys [dummy]. Path: .properties.[0]");
    }
}
