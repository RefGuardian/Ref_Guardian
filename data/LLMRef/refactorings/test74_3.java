public class test74 {

    private void testMultipleProviders(String specifiedEntityId, String expected) throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            server.start();
            String metadataUrl = getMetadataUrl(server);
            WebApplicationContextRunner contextRunner = this.contextRunner
                .withPropertyValues(PREFIX + ".foo.assertingparty.metadata-uri=" + metadataUrl);
            if (specifiedEntityId != null) {
                contextRunner = addSpecifiedEntityId(contextRunner, specifiedEntityId);
            }
            runContext(contextRunner, expected);
        }
    }

    private String getMetadataUrl(MockWebServer server) {
        return server.url("").toString();
    }

    private WebApplicationContextRunner addSpecifiedEntityId(WebApplicationContextRunner contextRunner, String specifiedEntityId) {
        return contextRunner
            .withPropertyValues(PREFIX + ".foo.assertingparty.entity-id=" + specifiedEntityId);
    }

    private void runContext(WebApplicationContextRunner contextRunner, String expected) {
        contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(RelyingPartyRegistrationRepository.class);
            assertThat(server.getRequestCount()).isOne();
            RelyingPartyRegistrationRepository repository = context
                .getBean(RelyingPartyRegistrationRepository.class);
            RelyingPartyRegistration registration = repository.findByRegistrationId("foo");
            assertThat(registration.getAssertingPartyMetadata().getEntityId()).isEqualTo(expected);
        });
    }
}
