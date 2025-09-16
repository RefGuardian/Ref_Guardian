public class test23 {

    private static final String SPRING_DOCKER_COMPOSE_ARGUMENTS = "spring.docker.compose.arguments";
    private static final String SPRING_DOCKER_COMPOSE_FILE = "spring.docker.compose.file";
    private static final String SPRING_DOCKER_COMPOSE_LIFECYCLE_MANAGEMENT = "spring.docker.compose.lifecycle-management";
    private static final String SPRING_DOCKER_COMPOSE_HOST = "spring.docker.compose.host";
    private static final String SPRING_DOCKER_COMPOSE_START_COMMAND = "spring.docker.compose.start.command";
    private static final String SPRING_DOCKER_COMPOSE_STOP_COMMAND = "spring.docker.compose.stop.command";
    private static final String SPRING_DOCKER_COMPOSE_STOP_TIMEOUT = "spring.docker.compose.stop.timeout";
    private static final String SPRING_DOCKER_COMPOSE_PROFILES_ACTIVE = "spring.docker.compose.profiles.active";
    private static final String SPRING_DOCKER_COMPOSE_READINESS_WAIT = "spring.docker.compose.readiness.wait";
    private static final String SPRING_DOCKER_COMPOSE_READINESS_TIMEOUT = "spring.docker.compose.readiness.timeout";
    private static final String SPRING_DOCKER_COMPOSE_READINESS_TCP_CONNECT_TIMEOUT = "spring.docker.compose.readiness.tcp.connect-timeout";
    private static final String SPRING_DOCKER_COMPOSE_READINESS_TCP_READ_TIMEOUT = "spring.docker.compose.readiness.tcp.read-timeout";

    @Test
    void getWhenPropertiesReturnsBound() {
        Map<String, String> source = new LinkedHashMap<>();
        source.put(SPRING_DOCKER_COMPOSE_ARGUMENTS, "--project-name=test,--progress=auto");
        source.put(SPRING_DOCKER_COMPOSE_FILE, "my-compose.yml");
        source.put(SPRING_DOCKER_COMPOSE_LIFECYCLE_MANAGEMENT, "start-only");
        source.put(SPRING_DOCKER_COMPOSE_HOST, "myhost");
        source.put(SPRING_DOCKER_COMPOSE_START_COMMAND, "start");
        source.put(SPRING_DOCKER_COMPOSE_STOP_COMMAND, "down");
        source.put(SPRING_DOCKER_COMPOSE_STOP_TIMEOUT, "5s");
        source.put(SPRING_DOCKER_COMPOSE_PROFILES_ACTIVE, "myprofile");
        source.put(SPRING_DOCKER_COMPOSE_READINESS_WAIT, "only-if-started");
        source.put(SPRING_DOCKER_COMPOSE_READINESS_TIMEOUT, "10s");
        source.put(SPRING_DOCKER_COMPOSE_READINESS_TCP_CONNECT_TIMEOUT, "400ms");
        source.put(SPRING_DOCKER_COMPOSE_READINESS_TCP_READ_TIMEOUT, "500ms");
        Binder binder = new Binder(new MapConfigurationPropertySource(source));
        DockerComposeProperties properties = DockerComposeProperties.get(binder);
        assertThat(properties.getArguments()).containsExactly("--project-name=test", "--progress=auto");
        assertThat(properties.getFile()).containsExactly(new File("my-compose.yml"));
        assertThat(properties.getLifecycleManagement()).isEqualTo(LifecycleManagement.START_ONLY);
        assertThat(properties.getHost()).isEqualTo("myhost");
        assertThat(properties.getStart().getCommand()).isEqualTo(StartCommand.START);
        assertThat(properties.getStop().getCommand()).isEqualTo(StopCommand.DOWN);
        assertThat(properties.getStop().getTimeout()).isEqualTo(Duration.ofSeconds(5));
        assertThat(properties.getProfiles().getActive()).containsExactly("myprofile");
        assertThat(properties.getReadiness().getWait()).isEqualTo(Wait.ONLY_IF_STARTED);
        assertThat(properties.getReadiness().getTimeout()).isEqualTo(Duration.ofSeconds(10));
        assertThat(properties.getReadiness().getTcp().getConnectTimeout()).isEqualTo(Duration.ofMillis(400));
        assertThat(properties.getReadiness().getTcp().getReadTimeout()).isEqualTo(Duration.ofMillis(500);
    }
}
