public class test1 {

    @ParameterizedTest
    	@CsvSource({ "reactive,netty", "reactive,tomcat", "servlet,tomcat", "reactive,undertow", "servlet,undertow" })
    	void home(String webStack, String server) {
    		try (ApplicationContainer serverContainer = new ServerApplicationContainer(webStack, server)) {
    			serverContainer.start();
    			waitForServer(serverContainer);
    			String serverLogs = serverContainer.getLogs();
    			assertThat(serverLogs).contains(SERVER_START_MESSAGES.get(server));
    			try (ApplicationContainer clientContainer = new ClientApplicationContainer()) {
    				clientContainer.start();
    				waitForClient(clientContainer);
    				String clientLogs = clientContainer.getLogs();
    				assertServerCalledWithName(clientLogs, PRIMARY_SERVER_NAME);
    				assertServerCalledWithName(clientLogs, ALT_SERVER_NAME);
    				clientContainer.stop();
    			}
    			serverContainer.stop();
    		}
    	}

    private void waitForServer(ApplicationContainer serverContainer) {
        try {
            Awaitility.await().atMost(Duration.ofSeconds(60)).until(serverContainer::isRunning);
        }
        catch (ConditionTimeoutException ex) {
            System.out.println(serverContainer.getLogs());
            throw ex;
        }
    }

    private void waitForClient(ApplicationContainer clientContainer) {
        Awaitility.await().atMost(Duration.ofSeconds(60)).until(() -> !clientContainer.isRunning());
    }
}
