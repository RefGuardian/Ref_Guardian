public class test1 {

    @ParameterizedTest
    	@CsvSource({ "reactive,netty", "reactive,tomcat", "servlet,tomcat", "reactive,undertow", "servlet,undertow" })
    	void home(String webStack, String server) {
    		try (ApplicationContainer serverContainer = new ServerApplicationContainer(webStack, server)) {
    			serverContainer.start();
    			waitForServerToStart(serverContainer);
    			serverContainer.stop();
    		}
    	}

	private void waitForServerToStart(ApplicationContainer serverContainer) {
		try {
			Awaitility.await().atMost(Duration.ofSeconds(60)).until(serverContainer::isRunning);
		}
		catch (ConditionTimeoutException ex) {
			System.out.println(serverContainer.getLogs());
			throw ex;
		}
		String serverLogs = serverContainer.getLogs();
		assertThat(serverLogs).contains(SERVER_START_MESSAGES.get(server));
		try (ApplicationContainer clientContainer = new ClientApplicationContainer()) {
			clientContainer.start();
			waitForClientToStop(clientContainer);
			clientContainer.stop();
		}
	}

	private void waitForClientToStop(ApplicationContainer clientContainer) {
		Awaitility.await().atMost(Duration.ofSeconds(60)).until(() -> !clientContainer.isRunning());
		String clientLogs = clientContainer.getLogs();
		assertServerCalledWithName(clientLogs, PRIMARY_SERVER_NAME);
		assertServerCalledWithName(clientLogs, ALT_SERVER_NAME);
	}

	// Existing methods extracted as new methods
}
