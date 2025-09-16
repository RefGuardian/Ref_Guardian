public class test281 {

    private static final String JSON_DATA = """
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
                        "since": "v17",
                        "dummy": "dummy"
                    },
                    "deprecated": true
                }
            ], "hints": []
        }""";

    @Test
    void shouldCheckPropertyDeprecationFields() {
        String json = JSON_DATA;
        assertThatException().isThrownBy(() -> read(json))
            .withMessage(
                "Expected only keys [level, reason, replacement, since], but found additional keys [dummy]. Path: .properties.[0].deprecation");
    }
}
