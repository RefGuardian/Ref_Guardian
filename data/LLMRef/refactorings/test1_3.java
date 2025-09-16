public class test1 {

    @ParameterizedTest
    @CsvSource({ "reactive,netty", "reactive,tomcat", "servlet,tomcat", "reactive,undertow", "servlet,undertow" })
    void home(String webStack, String server) {
        try (ApplicationContainer serverContainer = new ServerApplicationContainer(webStack, server)) {
            serverContainer.start();
            assertServerIsRunning(serverContainer);
            String serverLogs = serverContainer.getLogs();
            assertThat(serverLogs).contains(SERVER_START_MESSAGES.get(server));
            try (ApplicationContainer clientContainer = new ClientApplicationContainer()) {
                clientContainer.start();
                assertClientIsNotRunning(clientContainer);
                String clientLogs = clientContainer.getLogs();
                assertServerCalledWithName(clientLogs, PRIMARY_SERVER_NAME);
                assertServerCalledWithName(clientLogs, ALT_SERVER_NAME);
                clientContainer.stop();
            }
            serverContainer.stop();
        }
    }

    private void assertServerIsRunning(ApplicationContainer serverContainer) {
        Awaitility.await().atMost(Duration.ofSeconds(60)).until(serverContainer::isRunning);
    }

    private void assertClientIsNotRunning(ApplicationContainer clientContainer) {
        Awaitility.await().atMost(Duration.ofSeconds(60)).until(() -> !clientContainer.isRunning());
    }

    // Constants
    private static final String PRIMARY_SERVER_NAME = "primary_server";
    private static final String ALT_SERVER_NAME = "alternative_server";
    private static final Map<String, String> SERVER_START_MESSAGES = new HashMap<>();

    static {
        SERVER_START_MESSAGES.put("netty", "netty_start");
        SERVER_START_MESSAGES.put("tomcat", "tomcat_start");
        SERVER_START_MESSAGES.put("undertow", "undertow_start");
    }
}
