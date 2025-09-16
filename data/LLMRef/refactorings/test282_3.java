public class test282 {

    @Test
    void shouldCheckHintValueFields() {
        String hintJson = """
                {
                    "groups": [], "properties": [], "hints": [
                        {
                            "name": "name",
                            "values": [
                                {
                                    "value": "value",
                                    "description": "some description",
                                    "dummy": "dummy"
                                }
                            ],
                            "providers": []
                        }
                    ]
                }""";
        assertThatException().isThrownBy(() -> read(hintJson))
                .withMessage(
                        "Expected only keys [description, value], but found additional keys [dummy]. Path: .hints.[0].values.[0]");
    }
}
