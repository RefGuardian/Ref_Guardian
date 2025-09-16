public class test74 {

    private void testMultipleProviders(String specifiedEntityId, String expected) throws Exception {
    		try (MockWebServer server = new MockWebServer()) {
    			server.start();
    			String metadataUrl = server.url("").toString();
    			setupMockResponse(server, new ClassPathResource("idp-metadata-with-multiple-providers"));
    			WebApplicationContextRunner contextRunner = this.contextRunner
    				.withPropertyValues(PREFIX + ".foo.assertingparty.metadata-uri=" + metadataUrl);
    			if (specifiedEntityId != null) {
    				contextRunner = contextRunner
    					.withPropertyValues(PREFIX + ".foo.assertingparty.entity-id=" + specifiedEntityId);
    			}
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
}
