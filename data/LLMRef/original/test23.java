public class test23 {

    @Test
    	void getWhenPropertiesReturnsBound() {
    		Map<String, String> source = new LinkedHashMap<>();
    		source.put("spring.docker.compose.arguments", "--project-name=test,--progress=auto");
    		source.put("spring.docker.compose.file", "my-compose.yml");
    		source.put("spring.docker.compose.lifecycle-management", "start-only");
    		source.put("spring.docker.compose.host", "myhost");
    		source.put("spring.docker.compose.start.command", "start");
    		source.put("spring.docker.compose.stop.command", "down");
    		source.put("spring.docker.compose.stop.timeout", "5s");
    		source.put("spring.docker.compose.profiles.active", "myprofile");
    		source.put("spring.docker.compose.readiness.wait", "only-if-started");
    		source.put("spring.docker.compose.readiness.timeout", "10s");
    		source.put("spring.docker.compose.readiness.tcp.connect-timeout", "400ms");
    		source.put("spring.docker.compose.readiness.tcp.read-timeout", "500ms");
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
    		assertThat(properties.getReadiness().getTcp().getReadTimeout()).isEqualTo(Duration.ofMillis(500));
    	}
}
